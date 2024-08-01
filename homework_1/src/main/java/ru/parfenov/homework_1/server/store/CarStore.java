package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;

import java.util.List;

public interface CarStore {
    Car create(User user, String brand, String model, int yearOfProd, int price, CarCondition condition);
    Car findById(int id);
    Car update(Car car);
    Car delete(Car car);
    List<Car> findAll();
    List<Car> findByParameter(
            int id, User owner, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}