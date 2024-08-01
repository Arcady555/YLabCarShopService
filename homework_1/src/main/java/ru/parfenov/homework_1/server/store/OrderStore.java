package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;

import java.util.List;

public interface OrderStore {
    Order create(User user, int carId, OrderType type);
    Order findById(int id);
    Order update(Order order);
    Order delete(Order order);
    List<Order> findAll();
    List<Order> findByParameter(int id, String author, int carId, OrderType type, OrderStatus status);
}