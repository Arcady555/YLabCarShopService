package ru.parfenov.homework_2.store;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.utility.Utility;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CarStoreJdbcImpl implements CarStore {
    private final Connection connection;

    public CarStoreJdbcImpl() throws Exception {
        InputStream in = UserStoreJdbcImpl.class.getClassLoader()
                .getResourceAsStream("db/liquibase.properties");
        this.connection = Utility.loadConnection(in);
    }

    public CarStoreJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Car create(Car car) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO cs_schema.cars(" +
                        "owner_id," +
                        " brand," +
                        " model," +
                        " year_of_prod," +
                        " price," +
                        " condition" +
                        ")" +
                        " VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, car.getOwnerId());
            statement.setString(2, car.getBrand());
            statement.setString(3, car.getModel());
            statement.setInt(4, car.getYearOfProd());
            statement.setInt(5, car.getPrice());
            statement.setString(6, car.getCondition().toString());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.create(). ", e);;
        }
        return car;
    }

    @Override
    public Car findById(int id) {
        Car car = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.cars WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    car = returnCar(resultSet);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.findById(). ", e);
        }
        return car;
    }

    @Override
    public List<Car> findByOwner(int ownerId) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.cars WHERE owner_id = ?")) {
            statement.setInt(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.findByOwner(). ", e);
        }
        return cars;
    }


    @Override
    public Car update(Car car) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.cars SET owner_Id = ?," +
                        " brand = ?," +
                        " model = ?," +
                        " year_of_prod = ?," +
                        " price = ?," +
                        " car_condition = ?," +
                        " WHERE id = ?")
        ) {
            statement.setInt(1, car.getOwnerId());
            statement.setString(2, car.getBrand());
            statement.setString(3, car.getModel());
            statement.setInt(4, car.getYearOfProd());
            statement.setInt(5, car.getPrice());
            statement.setString(6, car.getCondition().toString());
            statement.setInt(7, car.getId());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.update(). ", e);
        }
        return car;
    }

    @Override
    public void delete(Car car) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE cs_schema.cars delete WHERE id = ?")) {
            statement.setInt(1, car.getId());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.delete(). ", e);
        }
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.cars")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.findAll(). ", e);
        }
        return cars;
    }

    @Override
    public List<Car> findByParameter(
            int id, int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    ) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM cs_schema.cars WHERE" +
                        " id = ?," +
                        " owner_Id = ?," +
                        " brand = ?," +
                        " model = ?," +
                        " year_of_prod = ?," +
                        " price > ?," +
                        " price < ?," +
                        " car_condition = ?")
        ) {
            statement.setInt(1, id);
            statement.setInt(1, ownerId);
            statement.setString(2, brand);
            statement.setString(3, model);
            statement.setInt(4, yearOfProd);
            statement.setInt(5, priceFrom);
            statement.setInt(6, priceTo);
            statement.setString(7, condition.toString());
            statement.setInt(8, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarStoreJdbcImpl.findByParameter(). ", e);
        }
        return cars;
    }

    private Car returnCar(ResultSet resultSet) throws SQLException {
        return new Car(
                resultSet.getInt("id"),
                resultSet.getInt("owner_id"),
                resultSet.getString("brand"),
                resultSet.getString("model"),
                resultSet.getInt("year_of_prod"),
                resultSet.getInt("price"),
                "new".equals(resultSet.getString("car_condition")) ? CarCondition.NEW : CarCondition.USED
        );
    }
}