package ru.parfenov.homework_3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.OrderStatus;
import ru.parfenov.homework_3.enums.OrderType;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.store.OrderStore;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class OrderServiceServletImpl implements OrderService, GettingIntFromString {
    private final OrderStore store;

    @Override
    public Optional<Order> create(int authorId, int carId, String typeStr) {
        OrderType type = getOrderTypeFromString(typeStr);
        return Optional.ofNullable(store.create(new Order(0, authorId, carId, type, OrderStatus.OPEN)));
    }

    @Override
    public Optional<Order> findById(String idStr) {
        int orderId = getIntFromString(idStr);
        return Optional.ofNullable(store.findById(orderId));
    }

    @Override
    public boolean isOwnOrder(int ownerId, String orderId) {
        int id = getIntFromString(orderId);
        return isOwnOrder(ownerId, id);
    }

    @Override
    public boolean isOwnOrder(int ownerId, int orderId) {
        Order order = store.findById(orderId);
        return order != null && order.getId() == ownerId;
    }

    @Override
    public boolean close(String orderIdStr) {
        int orderId = getIntFromString(orderIdStr);
        return store.update(orderId);
    }

    @Override
    public boolean delete(String idStr) {
        int id = getIntFromString(idStr);
        return store.delete(id);
    }

    @Override
    public List<Order> findAll() {
        return store.findAll();
    }

    @Override
    public List<Order> findByAuthor(int authorId) {
        return store.findByAuthor(authorId);
    }

    @Override
    public List<Order> findByParameter(String authorIdStr, String carIdStr, String typeStr, String statusStr) {
        int authorId = getIntFromString(authorIdStr);
        int carId = getIntFromString(carIdStr);
        OrderType type = getOrderTypeFromString(typeStr);
        OrderStatus status = "open".equals(statusStr) ?
                OrderStatus.OPEN :
                ("closed".equals(statusStr) ? OrderStatus.CLOSED : null);
        return store.findByParameter(authorId, carId, type, status);
    }

    private OrderType getOrderTypeFromString(String str) {
        return "buy".equals(str) ?
                OrderType.BUY :
                ("service".equals(str) ? OrderType.SERVICE : null);
    }
}