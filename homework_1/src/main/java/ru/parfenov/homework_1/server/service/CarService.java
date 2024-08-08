package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;

import java.util.List;

public interface CarService {
    Car create(User user, String brand, String model, int yearOfProd, int price, CarCondition condition);

    Car findById(int id);

    List<Car> findByUser(int userId);

    void update(Car car);

    Car delete(Car car);

    void findAll();

    void findByParameter(
            int id, int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}