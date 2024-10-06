package ru.homework_5.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.homework_5.enums.CarCondition;
import ru.homework_5.model.Car;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о машинах
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

    /**
     * Поиск машины пол ID её собственника
     *
     * @param ownerId ID юзера-собственника машины
     * @return Car - сущность из блока ru/parfenov/homework_3/model.
     */
    List<Car> findByOwnerId(int ownerId);

    /**
     * Вывод списка всех машин
     *
     * @return List список всех машин в БД
     */
    List<Car> findAll();

    /**
     * Метод предлагает поиск авто по указанным параметрам
     *
     * @param ownerId    ID юзера-собственника машины
     * @param brand      марка машины
     * @param model      модель
     * @param yearOfProd год выпуска
     * @param priceFrom  цена от данного числа
     * @param priceTo    цена до данного числа
     * @param condition  состояние
     * @return List список таких машин
     */
    @Query("SELECT c FROM Car c WHERE " +
            "(:ownerId = -1 OR c.ownerId = :ownerId) AND " +
            "(:brand = '' OR c.brand = :brand) AND" +
            "(:model = '' OR c.model = :model) AND" +
            "(:yearOfProd = -1 OR c.yearOfProd = :yearOfProd) AND" +
            "(:priceFrom = -1 OR c.price > :priceFrom) AND" +
            "(:priceTo = -1 OR c.price < :priceTo) AND" +
            "(:condition IS NULL OR c.condition = :condition)"
    )
    List<Car> findByParameters(
            @Param("ownerId") int ownerId,
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("yearOfProd") int yearOfProd,
            @Param("priceFrom") int priceFrom,
            @Param("priceTo") int priceTo,
            @Param("condition") CarCondition condition
    );
}