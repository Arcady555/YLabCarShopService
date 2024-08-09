package ru.parfenov.homework_2.store;

import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.utility.Utility;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStoreJdbcImpl implements OrderStore {
    private final Connection connection;

    public OrderStoreJdbcImpl() throws Exception {
        InputStream in = UserStoreJdbcImpl.class.getClassLoader()
                .getResourceAsStream("db/liquibase.properties");
        this.connection = Utility.loadConnection(in);
    }

    public OrderStoreJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Order create(Order order) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO cs_schema.orders(" +
                        "author_id," +
                        " car_id," +
                        " order_type," +
                        " order_status," +
                        ")" +
                        " VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, order.getCarId());
            statement.setInt(2, order.getCarId());
            statement.setString(3, order.getType().toString());
            statement.setString(4, order.getStatus().toString());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order findById(int id) {
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.orders WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = returnOrder(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.orders SET authorId = ?," +
                        " car_id = ?," +
                        " order_type = ?," +
                        " order_status = ?," +
                        " WHERE id = ?")
        ) {
            statement.setInt(1, order.getAuthorId());
            statement.setInt(2, order.getCarId());
            statement.setString(3, order.getType().toString());
            statement.setString(4, order.getStatus().toString());
            statement.setInt(5, order.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order delete(Order order) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE cs_schema.orders delete WHERE id = ?")) {
            statement.setInt(1, order.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.orders")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = returnOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> findByAuthor(int authorId) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.orders WHERE author_id = ?")) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = returnOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> findByParameter(int id, int authorId, int carId, OrderType type, OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM cs_schema.orders WHERE" +
                        " id = ?," +
                        " authorId = ?," +
                        " car_id = ?," +
                        " order_type = ?," +
                        " order_status = ?")
        ) {
            statement.setInt(1, id);
            statement.setInt(2, authorId);
            statement.setInt(3, carId);
            statement.setString(4, type.toString());
            statement.setString(5, status.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = returnOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order returnOrder(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getInt("id"),
                resultSet.getInt("author_id"),
                resultSet.getInt("car_id"),
                "buy".equals(resultSet.getString("order_type")) ? OrderType.BUY : OrderType.SERVICE,
                "open".equals(resultSet.getString("order_status")) ? OrderStatus.OPEN : OrderStatus.CLOSED
        );
    }
}