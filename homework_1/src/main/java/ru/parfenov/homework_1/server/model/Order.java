package ru.parfenov.homework_1.server.model;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private List<User> authors = new ArrayList<>();
    private int carId;
    private OrderType type;
    private OrderStatus status = OrderStatus.OPEN;

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<User> getAuthors() {
        return authors;
    }

    public void setAuthors(List<User> authors) {
        this.authors = authors;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}