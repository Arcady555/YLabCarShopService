package ru.parfenov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;
import ru.parfenov.model.Order;
import ru.parfenov.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceServletImpl implements OrderService, GettingIntFromString {
    private final OrderRepository repo;

    @Autowired
    public OrderServiceServletImpl(OrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Order> create(int authorId, int carId, String typeStr) {
        OrderType type = getOrderTypeFromString(typeStr);
        return Optional.ofNullable(repo.create(new Order(0, authorId, carId, type, OrderStatus.OPEN)));
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(repo.findById(id));
    }

    @Override
    public boolean isOwnOrder(int authorId, String orderId) {
        int id = getIntFromString(orderId);
        return isOwnOrder(authorId, id);
    }

    @Override
    public boolean isOwnOrder(int authorId, int orderId) {
        Order order = repo.findById(orderId);
        return order != null && order.getId() == authorId;
    }

    @Override
    public boolean close(int orderId) {
        return repo.update(orderId);
    }

    @Override
    public boolean delete(int id) {
        return repo.delete(id);
    }

    @Override
    public List<Order> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Order> findByAuthor(int authorId) {
        return repo.findByAuthor(authorId);
    }

    @Override
    public List<Order> findByParameter(String authorIdStr, String carIdStr, String typeStr, String statusStr) {
        int authorId = getIntFromString(authorIdStr);
        int carId = getIntFromString(carIdStr);
        OrderType type = getOrderTypeFromString(typeStr);
        OrderStatus status = "open".equals(statusStr) ?
                OrderStatus.OPEN :
                ("closed".equals(statusStr) ? OrderStatus.CLOSED : null);
        return repo.findByParameter(authorId, carId, type, status);
    }

    private OrderType getOrderTypeFromString(String str) {
        return "buy".equals(str) ?
                OrderType.BUY :
                ("service".equals(str) ? OrderType.SERVICE : null);
    }
}