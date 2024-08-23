package ru.parfenov.homework_3.repository;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.utility.JdbcRequests;
import ru.parfenov.homework_3.utility.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CarRepositoryJdbcImpl implements CarRepository {
    private final Connection connection;

    public CarRepositoryJdbcImpl() throws Exception {
        this.connection = Utility.loadConnection();
    }

    public CarRepositoryJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Car create(Car car) {
        try (PreparedStatement statement = connection.prepareStatement(
                JdbcRequests.createCar,
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
            log.error("Exception in CarRepositoryJdbcImpl.create(). ", e);
        }
        return findById(car.getId());
    }

    @Override
    public Car findById(int id) {
        Car car = null;
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findCarById)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    car = returnCar(resultSet);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarRepositoryJdbcImpl.findById(). ", e);
        }
        return car;
    }

    @Override
    public List<Car> findByOwner(int ownerId) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findCarByOwner)) {
            statement.setInt(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarRepositoryJdbcImpl.findByOwner(). ", e);
        }
        return cars;
    }

    /**
     * Запрос в БД формируется из того, какие поля карточки машины были заполнены для изменения
     */
    @Override
    public boolean update(Car car) {
        int carId = car.getId();
        int ownerId = car.getOwnerId();
        String brand = car.getBrand();
        String model = car.getModel();
        int yearOfProd = car.getYearOfProd();
        int price = car.getPrice();
        CarCondition condition = car.getCondition();

        String request = getRequestForUpdate(ownerId,
                brand, model, yearOfProd, price, condition);

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.cars SET " + request + " WHERE id = ?")
        ) {
            int i = generateStatementSets(statement,
                    ownerId, brand, model, yearOfProd, price, 0, 0, condition);
            i++;
            statement.setInt(i, carId);
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in CarRepositoryJdbcImpl.update(). ", e);
        }
        return findById(carId) != null && checkUpdate(car);
    }

    @Override
    public boolean delete(int carId) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.deleteCar)) {
            statement.setInt(1, carId);
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in CarRepositoryJdbcImpl.delete(). ", e);
        }
        return findById(carId) == null;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findAllCars)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarRepositoryJdbcImpl.findAll(). ", e);
        }
        return cars;
    }

    /**
     * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id собственника, марка, модель, года выпуска, выше указанной цены,
     * ниже указанной цены, состояние.
     */
    @Override
    public List<Car> findByParameter(int ownerId, String brand, String model, int yearOfProd,
                                     int priceFrom, int priceTo, CarCondition condition) {
        String request = getRequestForFindByParam(ownerId,
                brand, model, yearOfProd, priceFrom, priceTo, condition);
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.cars WHERE " + request)
        ) {
            generateStatementSets(statement,
                    ownerId, brand, model, yearOfProd, 0, priceFrom, priceTo, condition);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = returnCar(resultSet);
                    cars.add(car);
                }
            }
        } catch (Exception e) {
            log.error("Exception in CarRepositoryJdbcImpl.findByParameter(). ", e);
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

    private String getRequestForFindByParam(int ownerId,
                                            String brand,
                                            String model,
                                            int yearOfProd,
                                            int priceFrom,
                                            int priceTo,
                                            CarCondition condition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                        ownerId != 0 ? " owner_id = ? and" : "").
                append(!brand.isEmpty() ? " brand = ? and" : "").
                append(!model.isEmpty() ? " model = ? and" : "").
                append(yearOfProd != 0 ? " year_of_prod = ? and" : "").
                append(priceFrom != 0 ? " price > ? and" : "").
                append(priceTo != 0 ? " price < ? and" : "").
                append(condition != null ? " car_condition = ?" : ""
                );
        if (stringBuilder.toString().endsWith("and")) stringBuilder.setLength(stringBuilder.length() - 3);

        return stringBuilder.toString();
    }

    private String getRequestForUpdate(int ownerId,
                                       String brand,
                                       String model,
                                       int yearOfProd,
                                       int price,
                                       CarCondition condition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(ownerId != 0 ? " owner_id = ? ," : "")
                .append(!brand.isEmpty() ? " brand = ? ," : "")
                .append(!model.isEmpty() ? " model = ? ," : "")
                .append(yearOfProd != 0 ? " year_of_prod = ? and" : "")
                .append(price != 0 ? " price = ? ," : "")
                .append(condition != null ? " car_condition = ?" : "");
        if (stringBuilder.toString().endsWith(",")) stringBuilder.setLength(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private int generateStatementSets(PreparedStatement statement,
                                      int ownerId,
                                      String brand,
                                      String model,
                                      int yearOfProd,
                                      int price,
                                      int priceFrom,
                                      int priceTo,
                                      CarCondition condition) throws SQLException {
        int result = 0;
        if (ownerId != 0) {
            result++;
            statement.setInt(result, ownerId);
        }
        if (!brand.isEmpty()) {
            result++;
            statement.setString(result, brand);
        }
        if (!model.isEmpty()) {
            result++;
            statement.setString(result, model);
        }
        if (yearOfProd != 0) {
            result++;
            statement.setInt(result, yearOfProd);
        }
        if (price != 0) {
            result++;
            statement.setInt(result, price);
        }
        if (priceFrom != 0) {
            result++;
            statement.setInt(result, priceFrom);
        }
        if (priceTo != 0) {
            result++;
            statement.setInt(result, priceTo);
        }
        if (condition != null) {
            result++;
            statement.setString(result, condition.toString());
        }
        return result;
    }

    private boolean checkUpdate(Car car) {
        Car carForCheck = findById(car.getId());
        boolean isOwnerId = car.getOwnerId() == 0 || car.getOwnerId() == carForCheck.getOwnerId();
        boolean isBrand = "".equals(car.getBrand()) || car.getBrand().equals(carForCheck.getBrand());
        boolean isModel = "".equals(car.getModel()) || car.getModel().equals(carForCheck.getModel());
        boolean isYearOfProd = car.getYearOfProd() == 0 || car.getYearOfProd() == carForCheck.getYearOfProd();
        boolean isPrice = car.getPrice() == 0 || car.getPrice() == carForCheck.getPrice();
        boolean isCondition = car.getCondition() == null || car.getCondition().equals(carForCheck.getCondition());

        return isOwnerId && isBrand && isModel && isYearOfProd && isPrice && isCondition;

    }
}