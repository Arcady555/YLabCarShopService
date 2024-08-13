package ru.parfenov.homework_2.store;

import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.model.Car;

import java.util.List;

public interface CarStore {
    Car create(Car car);

    Car findById(int id);

    List<Car> findByOwner(int ownerId);

    Car update(Car car);

    void delete(Car car);

    List<Car> findAll();

    /**
     * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id собственника, марка, модель, года выпуска, выше указанной цены,
     * ниже указанной цены, состояние.
     */

    List<Car> findByParameter(
            int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}