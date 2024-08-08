package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.store.CarStore;
import ru.parfenov.homework_1.server.utility.Utility;

import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */

public class CarServiceConsoleImpl implements CarService {
    private final CarStore store;

    public CarServiceConsoleImpl(CarStore store) {
        this.store = store;
    }

    @Override
    public Car create(User user, String brand, String model, int yearOfProd, int price, CarCondition condition) {
        Car car = store.create(new Car(0, user, brand, model, yearOfProd, price, condition));
        Utility.printCar(car);
        return car;
    }

    @Override
    public Car findById(int id) {
        Car car = store.findById(id);
        if (car == null) {
            System.out.println("Car not found!");
        } else {
            Utility.printCar(car);
        }
        return car;
    }

    @Override
    public List<Car> findByUser(int userId) {
        List<Car> result = store.findByUser(userId);
        for (Car car : result) {
            Utility.printCar(car);
        }
        return result;
    }

    @Override
    public void update(Car car) {
        store.update(car);
        Utility.printCar(car);
    }

    @Override
    public Car delete(Car car) {
        return store.delete(car);
    }

    @Override
    public void findAll() {
        for (Car car : store.findAll()) {
            Utility.printCar(car);
        }
    }

    @Override
    public void findByParameter(int id, int ownerId, String brand, String model, int yearOfProd, int priceFrom, int priceTo, CarCondition condition) {
        for (Car car : store.findByParameter(id, ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition)) {
            Utility.printCar(car);
        }
    }
}