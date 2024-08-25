package ru.parfenov.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;
import ru.parfenov.model.Order;
import ru.parfenov.utility.JdbcRequests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class OrderRepositoryJdbcImpl implements OrderRepository {
    private final Connection connection;

    @Autowired
    public OrderRepositoryJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Order create(Order order) {
        try (PreparedStatement statement = connection.prepareStatement(
                JdbcRequests.createOrder,
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
            log.error("Exception in OrderRepositoryJdbcImpl.create(). ", e);
        }
        return findById(order.getId());
    }

    @Override
    public Order findById(int id) {
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findOrderById)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = returnOrder(resultSet);
                }
            }
        } catch (Exception e) {
            log.error("Exception in OrderRepositoryJdbcImpl.findById(). ", e);
        }
        return order;
    }

    @Override
    public boolean update(int orderId) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.updateOrder)) {
            statement.setInt(1, orderId);
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in OrderRepositoryJdbcImpl.update(). ", e);
        }
        return findById(orderId) != null && findById(orderId).getStatus() == OrderStatus.CLOSED;
    }

    @Override
    public boolean delete(int orderId) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.deleteOrder)) {
            statement.setInt(1, orderId);
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in OrderRepositoryJdbcImpl.delete(). ", e);
        }
        return findById(orderId) == null;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findAllOrders)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = returnOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            log.error("Exception in OrderRepositoryJdbcImpl.findAll(). ", e);
        }
        return orders;
    }

    @Override
    public List<Order> findByAuthor(int authorId) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findOrderByAuthor)) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = returnOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            log.error("Exception in OrderRepositoryJdbcImpl.findByAuthor(). ", e);
        }
        return orders;
    }

    /**
     * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id автора заказа, id машины, тип заказа(продажа или сервис), статус(открыт или открыт)
     */
    @Override
    public List<Order> findByParameter(int authorId, int carId, OrderType type, OrderStatus status) {
        String request = getRequest(authorId, carId, type, status);
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.orders " + request)
        ) {
            generateStatementSets(statement, authorId, carId, type, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = returnOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            log.error("Exception in OrderRepositoryJdbcImpl.findByParameter(). ", e);
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

    private String getRequest(int authorId, int carId, OrderType type, OrderStatus status) {
        StringBuilder stringBuilder = new StringBuilder(" WHERE ");
        stringBuilder.append(authorId != 0 ? " author_id = ? and" : "").
                append(carId != 0 ? " car_id = ? and" : "").
                append(type != null ? " order_type = ? and" : "").
                append(status != null ? " order_status = ?" : "");
        if (stringBuilder.toString().endsWith("and")) stringBuilder.setLength(stringBuilder.length() - 3);

        return stringBuilder.toString();
    }

    /**
     * Метод используется для обработки пустых и не пустых полей, для PreparedStatement.
     * Можно сделать void, но на случай расширения в будущем пусть возвращает int, как в других хранилищах
     */
    private int generateStatementSets(PreparedStatement statement,
                                      int authorId,
                                      int carId,
                                      OrderType type,
                                      OrderStatus status) throws SQLException {
        int result = 0;
        if (authorId != 0) {
            result++;
            statement.setInt(result, authorId);
        }
        if (carId != 0) {
            result++;
            statement.setInt(result, carId);
        }
        if (type != null) {
            result++;
            statement.setString(result, type.toString());
        }
        if (status != null) {
            result++;
            statement.setString(result, status.toString());
        }
        return result;
    }
}