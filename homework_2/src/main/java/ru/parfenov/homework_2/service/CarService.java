package ru.parfenov.homework_2.service;

import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.model.Car;

import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface CarService {
    /**
     * Метод задействован при создании карточки машины пользователем
     */
    Car create(int ownerId, String brand, String model, int yearOfProd, int price, CarCondition condition);

    /**
     * Поиск машины по её ID
     */
    Car findById(int id);

    /**
     * Поиск машины по её собственнику
     */
    List<Car> findByOwner(int ownerId);

    /**
     * Изменение данных о машине
     */
    void update(
            int carId, int ownerId, String brand, String model,
            int yearOfProd, int price, CarCondition condition
    );

    /**
     * Удаление карточки машины
     */
    void delete(Car car);

    /**
     * Вывод списка всех машин
     */
    void findAll();

    /**
     * Поиск машин подпадающих под заданные параметры
     */
    void findByParameter(
            int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}