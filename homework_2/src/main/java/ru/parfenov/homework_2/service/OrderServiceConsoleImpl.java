package ru.parfenov.homework_2.service;

import lombok.AllArgsConstructor;
import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.store.OrderStore;
import ru.parfenov.homework_2.utility.Utility;

import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */

@AllArgsConstructor
public class OrderServiceConsoleImpl implements OrderService {
    private final OrderStore store;

    @Override
    public Order create(int authorId, int carId, OrderType orderType) {
        Order order = store.create(new Order(0, authorId, carId, orderType, OrderStatus.OPEN));
        Utility.printOrder(order);
        return order;
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
            orderList.add(new Order(0, 0, 0, null, null));
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
