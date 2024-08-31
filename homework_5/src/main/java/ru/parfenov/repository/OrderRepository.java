package ru.parfenov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;
import ru.parfenov.model.Order;

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
    @Query(
            "from Order o where o.authorId = ?1" +
                    " and o.carId = ?2" +
                    " and o.type = ?3" +
                    " and o.status = ?4"
    )
    List<Order> findByParameter(int authorId, int carId, OrderType type, OrderStatus status);
}