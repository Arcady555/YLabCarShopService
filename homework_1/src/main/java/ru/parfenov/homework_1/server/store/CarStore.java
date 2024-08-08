package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.model.Car;

import java.util.List;

public interface CarStore {
    Car create(Car car);

    Car findById(int id);

    List<Car> findByUser(int userId);

    Car update(Car car);

    Car delete(Car car);

    List<Car> findAll();

    /**
     * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id машины, id собственника, марка, модель, года выпуска, выше указанной цены,
     * ниже указанной цены, состояние.
     */

    List<Car> findByParameter(
            int id, int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}