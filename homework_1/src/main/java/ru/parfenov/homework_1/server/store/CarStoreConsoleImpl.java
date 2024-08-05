package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.model.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarStoreConsoleImpl implements CarStore {
    private static int carId = 0;
    private final Map<Integer, Car> carMap = new HashMap<>();

    @Override
    public Car create(Car car) {
        carId++;
        car.setId(carId);
        carMap.put(carId, car);
        return car;
    }

    @Override
    public Car findById(int id) {
        return carMap.get(id);
    }

    @Override
    public List<Car> findByUser(int userId) {
        List<Car> list = new ArrayList<>();
        for (Car car : findAll()) {
            if (car.getOwner().getId() == userId) {
                list.add(car);
            }
        }
        return list;
    }

    @Override
    public Car update(Car car) {
        return carMap.replace(car.getId(), car);
    }

    @Override
    public Car delete(Car car) {
        return carMap.remove(car.getId());
    }

    @Override
    public List<Car> findAll() {
        List<Car> list = new ArrayList<>();
        for (Map.Entry<Integer, Car> element : carMap.entrySet()) {
            list.add(element.getValue());
        }
        return list;
    }

    @Override
    public List<Car> findByParameter(
            int id, int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    ) {
        List<Car> allCars = findAll();
        List<Car> result = new ArrayList<>();
        for (Car car : allCars) {
            if (select(car, id, ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition)) {
                result.add(car);
            }
        }
        return result;
    }

    private boolean select(
            Car car, int id, int ownerId, String brand, String model,
            int yearOfProd, int priceFrom, int priceTo, CarCondition condition
    ) {
        boolean result = id == 0 || car.getId() == id;
        if (ownerId != 0 && car.getOwner().getId() != ownerId) {
            result = false;
        }
        if (!brand.isEmpty() && !car.getBrand().equals(brand)) {
            result = false;
        }
        if (!model.isEmpty() && !car.getModel().equals(model)) {
            result = false;
        }
        if ((yearOfProd < 1900 || yearOfProd > 2024) && car.getYearOfProd() <= yearOfProd) {
            result = false;
        }
        if (car.getPrice() <= priceFrom) {
            result = false;
        }
        if (priceTo != 0 && car.getPrice() >= priceFrom) {
            result = false;
        }
        if (condition != null && !car.getCondition().equals(condition)) {
            result = false;
        }
        return result;
    }
}