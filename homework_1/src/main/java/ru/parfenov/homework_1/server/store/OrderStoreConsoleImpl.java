package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStoreConsoleImpl implements OrderStore {
    private static int orderId = 0;

    private final Map<Integer, Order> orderMap = new HashMap<>();

    @Override
    public Order create(User user, int carId, OrderType type) {
        orderId++;
        Order order = new Order(orderId);
        order.getAuthors().add(user);
        order.setCarId(carId);
        order.setType(type);
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
    public List<Order> findByParameter(int id, String author, int carId, OrderType type, OrderStatus status) {
        List<Order> allOrders = findAll();
        List<Order> result = new ArrayList<>();
        for (Order order : allOrders) {
            if (select(order, id, author, carId, type, status)) {
                result.add(order);
            }
        }
        return result;
    }

    private boolean select(Order order, int id, String author, int carId, OrderType type, OrderStatus status) {
        boolean result = id == 0 || order.getId() == id;
        if (!author.isEmpty() && listContainsName(order.getAuthors(), author)) {
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

    private boolean listContainsName(List<User> list, String name) {
        boolean result = false;
        for (User user : list) {
            if (user.getName().contains(name)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
