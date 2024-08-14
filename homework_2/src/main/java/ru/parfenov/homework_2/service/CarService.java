package ru.parfenov.homework_2.service;

import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.model.Car;

import java.util.List;

public interface CarService {
    Car create(int ownerId, String brand, String model, int yearOfProd, int price, CarCondition condition);

    Car findById(int id);

    List<Car> findByOwner(int ownerId);

    void update(
            int carId, int ownerId, String brand, String model,
            int yearOfProd, int price, CarCondition condition
    );

    void delete(Car car);

    void findAll();

    void findByParameter(
            int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}