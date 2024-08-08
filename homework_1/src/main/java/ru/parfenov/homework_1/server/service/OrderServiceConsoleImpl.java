package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.store.OrderStore;
import ru.parfenov.homework_1.server.utility.Utility;

import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */

public class OrderServiceConsoleImpl implements OrderService {
    private final OrderStore store;

    public OrderServiceConsoleImpl(OrderStore store) {
        this.store = store;
    }

    @Override
    public void create(User user, int carId, OrderType orderType) {
        Order order = store.create(new Order(0, user, carId, orderType, OrderStatus.OPEN));
        Utility.printOrder(order);
    }

    @Override
    public Order findById(int id) {
        Order order = store.findById(id);
        if (order == null) {
            System.out.println("User not found!");
        } else {
            Utility.printOrder(order);
        }
        return order;
    }

    @Override
    public void update(Order order) {
        store.update(order);
        Utility.printOrder(order);
    }

    @Override
    public Order delete(Order order) {
        return store.delete(order);
    }

    @Override
    public void findAll() {
        for (Order order : store.findAll()) {
            Utility.printOrder(order);
        }
    }

    @Override
    public void findByAuthor(int authorId) {
        List<Order> orderList = store.findByAuthor(authorId);
        if (orderList.isEmpty()) {
            orderList.add(new Order(0, null, 0, null, null));
            System.out.println(System.lineSeparator() + "(You have no orders)");
        } else {
            for (Order order : orderList) {
                Utility.printOrder(order);
            }
        }
    }

    @Override
    public void findByParameter(int id, int authorId, int carId, OrderType type, OrderStatus status) {
        List<Order> orderList = store.findByParameter(id, authorId, carId, type, status);
        for (Order order : orderList) {
            Utility.printOrder(order);
        }
    }
}
