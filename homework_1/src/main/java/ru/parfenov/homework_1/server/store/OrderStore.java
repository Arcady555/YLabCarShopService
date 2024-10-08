package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;

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
     * id заказа, id автора заказа, id машины, тип заказа(продажа или сервис), статус(открыт или открыт)
     */

    List<Order> findByParameter(int id, int authorId, int carId, OrderType type, OrderStatus status);
}