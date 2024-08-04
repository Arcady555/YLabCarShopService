package ru.parfenov.homework_1.server.model;

import ru.parfenov.homework_1.server.enums.CarCondition;

public class Car {
    private int id;
    private User owner;
    private String brand;
    private String model;
    private int yearOfProd;
    private int price;
    private CarCondition condition;

    public Car(int id, User owner, String brand, String model, int yearOfProd, int price, CarCondition condition) {
        this.id = id;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.yearOfProd = yearOfProd;
        this.price = price;
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfProd() {
        return yearOfProd;
    }

    public void setYearOfProd(int yearOfProd) {
        this.yearOfProd = yearOfProd;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CarCondition getCondition() {
        return condition;
    }

    public void setCondition(CarCondition condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Car " +
                "id=" + id +
                ", owner=" + owner.toString() +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearOfProd=" + yearOfProd +
                ", price=" + price +
                ", condition=" + condition;
    }
}
