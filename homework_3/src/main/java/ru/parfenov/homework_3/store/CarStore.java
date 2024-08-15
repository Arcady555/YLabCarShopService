package ru.parfenov.homework_3.store;

import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.model.Car;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о машинах
 */
public interface CarStore {
    /**
     * Создание карточки машины
     */
    Car create(Car car);

    /**
     * Поиск машины по её уникальному ID
     */
    Car findById(int id);

    /**
     * Поиск машины пол ID её собственника
     */
    List<Car> findByOwner(int ownerId);

    /**
     * Метод предлагает обновление автомобиля.
     */
    Car update(Car car);

    /**
     * Удаление карточки машины
     */
    void delete(Car car);

    /**
     * Вывод списка всех машин
     */
    List<Car> findAll();

    /**
     * Метод предлагает поиск авто по указанным параметрам
     */
    List<Car> findByParameter(
            int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}