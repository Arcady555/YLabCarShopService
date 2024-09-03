package ru.homework_5.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
    @Query(
            "from Car c where c.ownerId = ?1" +
                    " and c.brand = ?2" +
                    " and c.model = ?3" +
                    " and c.yearOfProd = ?4" +
                    " and c.price > ?5" +
                    " and c.price < ?6" +
                    " and c.condition = ?7"
    )
    List<Car> findByParameters(int ownerId, String brand, String model, int yearOfProd, int priceFrom, int priceTo,
                               CarCondition condition );
}