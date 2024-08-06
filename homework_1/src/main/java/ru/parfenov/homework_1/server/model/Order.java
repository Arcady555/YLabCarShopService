package ru.parfenov.homework_1.server.model;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;


public class Order {
    private int id;
    private User author;
    private int carId;
    private OrderType type;
    private OrderStatus status;

    public Order() {
    }

    public Order(int id, User author, int carId, OrderType type, OrderStatus status) {
        this.id = id;
        this.author = author;
        this.carId = carId;
        this.type = type;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Order " +
                "id=" + id +
                ", author=" + author +
                ", carId=" + carId +
                ", type=" + type +
                ", status=" + status;
    }
}