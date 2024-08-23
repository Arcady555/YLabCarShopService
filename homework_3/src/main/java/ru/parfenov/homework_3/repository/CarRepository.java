package ru.parfenov.homework_3.repository;

import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.model.Car;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о машинах
 */
public interface CarRepository {
    /**
     * Создание карточки машины
     * @param car Car - сущность из блока ru/parfenov/homework_3/model
     * @return Car - сущность из блока ru/parfenov/homework_3/model
     */
    Car create(Car car);

    /**
     * Поиск машины по её уникальному ID
     * @param id машины, полученный при её создании, заведении карточки в БД
     * @return Car - сущность из блока ru/parfenov/homework_3/model.
     */
    Car findById(int id);

    /**
     * Поиск машины пол ID её собственника
     * @param ownerId ID юзера-собственника машины
     * @return Car - сущность из блока ru/parfenov/homework_3/model.
     */
    List<Car> findByOwner(int ownerId);

    /**
     * Метод предлагает обновление автомобиля.
     * @param car Car - сущность из блока ru/parfenov/homework_3/model.
     * @return получилось или нет обновить
     */
    boolean update(Car car);

    /**
     * Удаление карточки машины
     * @param carId машины, полученный при её создании, заведении карточки в БД
     * @return получилось или нет удалить
     */
    boolean delete(int carId);

    /**
     * Вывод списка всех машин
     * @return List список всех машин в БД
     */
    List<Car> findAll();

    /**
     * Метод предлагает поиск авто по указанным параметрам
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
            int ownerId, String brand, String model, int yearOfProd,
            int priceFrom, int priceTo, CarCondition condition
    );
}