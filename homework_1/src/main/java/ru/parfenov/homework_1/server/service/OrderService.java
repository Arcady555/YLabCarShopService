package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;

public interface OrderService {
    void create(User user, int carId, OrderType type);

    Order findById(int id);

    void update(Order order);

    Order delete(Order order);

    void findAll();

    void findByAuthor(int authorId);

    void findByParameter(int id, int authorId, int carId, OrderType type, OrderStatus status);
}