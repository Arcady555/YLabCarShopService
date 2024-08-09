package ru.parfenov.homework_2.server.service;

import ru.parfenov.homework_2.server.enums.CarCondition;
import ru.parfenov.homework_2.server.model.Car;
import ru.parfenov.homework_2.server.model.User;

import java.util.List;

public interface CarService {
    Car create(int ownerId, String brand, String model, int yearOfProd, int price, CarCondition condition);

    Car findById(int id);

    List<Car> findByOwner(int ownerId);

    void update(Car car);

    void delete(Car car);

    void findAll();

    void findByParameter(
            int id, int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}