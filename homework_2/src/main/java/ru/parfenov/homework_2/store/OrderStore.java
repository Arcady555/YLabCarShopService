package ru.parfenov.homework_2.store;

import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.model.Order;

import java.util.List;

public interface OrderStore {
    Order create(Order order);

    Order findById(int id);

    Order update(Order order);

    Order delete(Order order);

    List<Order> findAll();

    List<Order> findByAuthor(int authorId);

    /**
     * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id автора заказа, id машины, тип заказа(продажа или сервис), статус(открыт или открыт)
     */

    List<Order> findByParameter(int authorId, int carId, OrderType type, OrderStatus status);
}