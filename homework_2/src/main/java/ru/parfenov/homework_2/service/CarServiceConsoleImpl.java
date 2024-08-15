package ru.parfenov.homework_2.service;

import lombok.AllArgsConstructor;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.store.CarStore;
import ru.parfenov.homework_2.utility.Utility;

import java.util.List;

@AllArgsConstructor
public class CarServiceConsoleImpl implements CarService {
    private final CarStore store;

    @Override
    public Car create(int ownerId, String brand, String model, int yearOfProd, int price, CarCondition condition) {
        Car car = store.create(new Car(0, ownerId, brand, model, yearOfProd, price, condition));
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
    public List<Car> findByOwner(int ownerId) {
        List<Car> result = store.findByOwner(ownerId);
        for (Car car : result) {
            Utility.printCar(car);
        }
        return result;
    }

    @Override
    public void update(int carId, int ownerId, String brand, String model, int yearOfProd, int price, CarCondition condition) {
        Car car = new Car(carId, ownerId, brand, model, yearOfProd, price, condition);
        store.update(car);
        Utility.printCar(car);
    }

    @Override
    public void delete(Car car) {
        store.delete(car);
    }

    @Override
    public void findAll() {
        for (Car car : store.findAll()) {
            Utility.printCar(car);
        }
    }

    @Override
    public void findByParameter(int ownerId, String brand, String model, int yearOfProd, int priceFrom, int priceTo, CarCondition condition) {
        for (Car car : store.findByParameter(ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition)) {
            Utility.printCar(car);
        }
    }
}