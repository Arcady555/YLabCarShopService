package ru.homework_5.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.homework_5.model.Order;
import ru.homework_5.enums.OrderStatus;
import ru.homework_5.enums.OrderType;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о заказах
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    /**
     * Вывод списка всех заказов
     *
     * @return List список всех заказов
     */
    @Override
    List<Order> findAll();

    /**
     * Поиск заказа по его создателю
     *
     * @param authorId ID юзера - создателя заказов
     * @return List список таких заказов
     */
    List<Order> findByAuthorId(int authorId);

    /**
     * Метод предлагает поиск заказа по указанным параметрам
     *
     * @param authorId ID юзера-создателя заказа
     * @param carId    id машины, полученный при её создании, заведении карточки в БД
     * @param type     тип заказа - продажа или сервис
     * @param status   статус заказа - открыт или закрыт
     * @return List список таких заказов
     */
    @Query("SELECT o FROM Order o WHERE " +
            "(:authorId = -1 OR o.authorId = :authorId) AND " +
            "(:carId = -1 OR o.carId = :carId) AND " +
            "(:type IS NULL OR o.type = :type) AND" +
            "(:status IS NULL OR o.status = :status)"
    )
    List<Order> findByParameter(
            @Param("authorId") int authorId,
            @Param("carId") int carId,
            @Param("type") OrderType type,
            @Param("status") OrderStatus status
    );
}