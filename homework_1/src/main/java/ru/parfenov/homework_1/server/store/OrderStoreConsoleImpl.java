package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStoreConsoleImpl implements OrderStore {
    private static int orderId = 0;

    private final Map<Integer, Order> orderMap = new HashMap<>();

    @Override
    public Order create(Order order) {
        order.setId(orderId++);
        orderMap.put(orderId, order);
        return order;
    }

    @Override
    public Order findById(int id) {
        return orderMap.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderMap.replace(order.getId(), order);
    }

    @Override
    public Order delete(Order order) {
        return orderMap.remove(order.getId());
    }

    @Override
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        for (Map.Entry<Integer, Order> element : orderMap.entrySet()) {
            list.add(element.getValue());
        }
        return list;
    }

    @Override
    public List<Order> findByAuthor(int authorId) {
        List<Order> result = new ArrayList<>();
        for (Order order : findAll()) {
            if (authorId == order.getAuthor().getId()) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> findByParameter(int id, int authorId, int carId, OrderType type, OrderStatus status) {
        List<Order> result = new ArrayList<>();
        for (Order order : findAll()) {
            if (select(order, id, authorId, carId, type, status)) {
                result.add(order);
            }
        }
        return result;
    }

    private boolean select(Order order, int id, int authorId, int carId, OrderType type, OrderStatus status) {
        boolean result = id == 0 || order.getId() == id;
        if (authorId != 0 && order.getAuthor().getId() != authorId) {
            result = false;
        }
        if (carId != 0 && order.getCarId() != carId) {
            result = false;
        }
        if (type != null && !order.getType().equals(type)) {
            result = false;
        }
        if (status != null && !order.getStatus().equals(status)) {
            result = false;
        }
        return result;
    }
}
