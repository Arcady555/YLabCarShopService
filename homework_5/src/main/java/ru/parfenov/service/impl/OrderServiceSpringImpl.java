package ru.parfenov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;
import ru.parfenov.model.Car;
import ru.parfenov.model.Order;
import ru.parfenov.repository.OrderRepository;
import ru.parfenov.service.OrderService;

import java.util.List;
import java.util.Optional;

import static ru.parfenov.utility.Utility.getIntFromString;

@Slf4j
@Service
public class OrderServiceSpringImpl implements OrderService {
    private final OrderRepository repo;

    @Autowired
    public OrderServiceSpringImpl(OrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Order> create(int authorId, int carId, String typeStr) {
        OrderType type = getOrderTypeFromString(typeStr);
        return Optional.of(repo.save(new Order(0, authorId, carId, type, OrderStatus.OPEN)));
    }

    @Override
    public Optional<Order> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public boolean isOwnOrder(int authorId, String orderId) {
        int id = getIntFromString(orderId);
        return isOwnOrder(authorId, id);
    }

    @Override
    public boolean isOwnOrder(int authorId, int orderId) {
        boolean result = false;
        Optional<Order> orderOptional = findById(orderId);
        if (orderOptional.isPresent()) {
            result = orderOptional.get().getAuthorId() == authorId;
        }
        return result;
    }

    @Override
    public boolean close(int orderId) {
        Optional<Order> order = findById(orderId);
        order.ifPresent(repo::save);
        return repo.findById(orderId).get().getStatus().equals(OrderStatus.CLOSED);
    }

    @Override
    public boolean delete(int id) {
        Optional<Order> order = findById(id);
        order.ifPresent(repo::delete);
        return !repo.existsById(id);
    }

    @Override
    public List<Order> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Order> findByAuthorId(int authorId) {
        return repo.findByAuthorId(authorId);
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