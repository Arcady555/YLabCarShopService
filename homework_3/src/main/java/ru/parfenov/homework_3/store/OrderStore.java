package ru.parfenov.homework_3.store;

import ru.parfenov.homework_3.enums.OrderStatus;
import ru.parfenov.homework_3.enums.OrderType;
import ru.parfenov.homework_3.model.Order;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о заказах
 */
public interface OrderStore {
    /**
     * Создание карточки заказа
     */
    Order create(Order order);

    /**
     * Поиск заказа по его уникальному ID
     */
    Order findById(int id);

    /**
     * Метод предлагает обновление заказа.
     */
    Order update(Order order);

    /**
     * Удаление карточки заказа
     */
    Order delete(Order order);

    /**
     * Вывод списка всех заказов
     */
    List<Order> findAll();

    /**
     * Поиск заказа по его создателю
     */
    List<Order> findByAuthor(int authorId);

    /**
     * Метод предлагает поиск заказа по указанным параметрам
     */
    List<Order> findByParameter(int authorId, int carId, OrderType type, OrderStatus status);
}