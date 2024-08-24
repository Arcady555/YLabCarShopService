package ru.parfenov.service;

import ru.parfenov.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface CarService {
    /**
     * Метод задействован при создании карточки машины пользователем
     * @param ownerId ID юзера-собственника машины
     * @param brand марка машины
     * @param model модель
     * @param yearOfProd год выпуска
     * @param price цена
     * @param condition состояние
     * @return Car - сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Car> create(int ownerId, String brand, String model, int yearOfProd, int price, String condition);

    /**
     * Поиск машины по её ID
     * @param id машины, полученный при её создании, заведении карточки в БД
     * @return Car - сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Car> findById(String id);

    /**
     * Поиск машины по её собственнику
     * @param ownerId ID юзера-собственника машины
     * @return Car - сущность из блока ru/parfenov/homework_3/model
     */
    List<Car> findByOwner(int ownerId);

    /**
     * Является ли машина собственностью юзера
     * Два метода, во втором ID машины в String
     * @param ownerId ID юзера - вероятного собственника машины
     * @param carId ID машины, полученный при её создании, заведении карточки в БД
     * @return true или false
     */
    boolean isOwnCar(int ownerId, int carId);
    boolean isOwnCar(int ownerId, String carId);

    /**
     * Изменение данных о машине
     * @param carId id машины, полученный при её создании, заведении карточки в БД
     * @param ownerId ID юзера-собственника машины
     * @param brand марка машины
     * @param model модель
     * @param yearOfProd год выпуска
     * @param price цена
     * @param condition состояние
     * @return boolean - получилось обновить данные по машине или нет
     */
    boolean update(
            int carId, int ownerId, String brand, String model,
            int yearOfProd, int price, String condition
    );

    /**
     * Удаление карточки машины
     * @param carId id машины, полученный при её создании, заведении карточки в БД
     * @return boolean - получилось удалить данные по машине или нет
     */
    boolean delete(String carId);

    /**
     * Вывод списка всех машин
     * @return List список всех машин в БД
     */
    List<Car> findAll();

    /**
     * Поиск машин подпадающих под заданные параметры
     * @param ownerId ID юзера-собственника машины
     * @param brand марка машины
     * @param model модель
     * @param yearOfProd год выпуска
     * @param priceFrom цена от данного числа
     * @param priceTo цена до данного числа
     * @param condition состояние
     * @return List список таких машин
     */
    List<Car> findByParameter(
            String ownerId, String brand, String model, String yearOfProd,
            String priceFrom, String priceTo, String condition
    );
}