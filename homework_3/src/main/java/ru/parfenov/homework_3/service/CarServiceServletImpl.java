package ru.parfenov.homework_3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.store.CarStore;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CarServiceServletImpl implements CarService, GettingIntFromString {
    private final CarStore store;

    @Override
    public Optional<Car> create(int ownerId, String brand, String model, int yearOfProd, int price, String conditionStr) {
        CarCondition condition = getCarConditionFromString(conditionStr);
        return Optional.ofNullable(store.create(new Car(0, ownerId, brand, model, yearOfProd, price, condition)));
    }

    @Override
    public Optional<Car> findById(String idStr) {
        int carId = getIntFromString(idStr);
        return Optional.ofNullable(store.findById(carId));
    }

    @Override
    public List<Car> findByOwner(int ownerId) {
        return store.findByOwner(ownerId);
    }

    @Override
    public boolean isOwnCar(int ownerId, int carId) {
        Car car = store.findById(carId);
        return car != null && car.getId() == ownerId;
    }

    @Override
    public boolean isOwnCar(int ownerId, String carId) {
        int id = getIntFromString(carId);
        return isOwnCar(ownerId, id);
    }

    @Override
    public boolean update(int carId, int ownerId, String brand, String model, int yearOfProd, int price, String conditionStr) {
        CarCondition condition = getCarConditionFromString(conditionStr);
        Car car = new Car(carId, ownerId, brand, model, yearOfProd, price, condition);
        return store.update(car);
    }

    @Override
    public boolean delete(String idStr) {
        int id = getIntFromString(idStr);
        return store.delete(id);
    }

    @Override
    public List<Car> findAll() {
        return store.findAll();
    }

    @Override
    public List<Car> findByParameter(
            String ownerIdStr,
            String brand,
            String model,
            String yearOfProdStr,
            String priceFromStr,
            String priceToStr,
            String conditionStr
    ) {
        int ownerId = getIntFromString(ownerIdStr);
        int yearOfProd = getIntFromString(yearOfProdStr);
        int priceFrom = getIntFromString(priceFromStr);
        int priceTo = getIntFromString(priceToStr);
        CarCondition condition = getCarConditionFromString(conditionStr);
        return store.findByParameter(ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition);
    }

    private CarCondition getCarConditionFromString(String str) {
        return "new".equals(str) ?
                CarCondition.NEW :
                ("used".equals(str) ? CarCondition.NEW : null);
    }
}