package ru.parfenov.homework_3.service;

import ru.parfenov.homework_3.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface CarService {
    /**
     * Метод задействован при создании карточки машины пользователем
     */
    Optional<Car> create(int ownerId, String brand, String model, int yearOfProd, int price, String condition);

    /**
     * Поиск машины по её ID
     */
    Optional<Car> findById(String id);

    /**
     * Поиск машины по её собственнику
     */
    List<Car> findByOwner(int ownerId);

    /**
     * Изменение данных о машине
     */
    boolean update(
            int carId, int ownerId, String brand, String model,
            int yearOfProd, int price, String condition
    );

    /**
     * Удаление карточки машины
     */
    boolean delete(String carId);

    /**
     * Вывод списка всех машин
     */
    List<Car> findAll();

    /**
     * Поиск машин подпадающих под заданные параметры
     */
    List<Car> findByParameter(
            String ownerId, String brand, String model, String yearOfProd,
            String priceFrom, String priceTo, String condition
    );
}