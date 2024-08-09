package ru.parfenov.homework_2.service;

import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.model.Order;

public interface OrderService {
    void create(int authorId, int carId, OrderType type);

    Order findById(int id);

    void update(Order order);

    Order delete(Order order);

    void findAll();

    void findByAuthor(int authorId);

    void findByParameter(int id, int authorId, int carId, OrderType type, OrderStatus status);
}