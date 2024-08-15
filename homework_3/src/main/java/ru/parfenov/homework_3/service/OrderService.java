package ru.parfenov.homework_3.service;

import ru.parfenov.homework_3.enums.OrderStatus;
import ru.parfenov.homework_3.enums.OrderType;
import ru.parfenov.homework_3.model.Order;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface OrderService {

    /**
     * Метод задействован при создании карточки заказа пользователем
     */
    Order create(int authorId, int carId, OrderType type);

    /**
     * Поиск заказа по его ID
     */
    Order findById(int id);

    /**
     * Закрытие заказа
     */
    void close(Order order);

    /**
     * Удаление заказа
     */
    Order delete(Order order);

    /**
     * Вывод всех заказов
     */
    void findAll();

    /**
     * Вывод заказов по их создателю
     */
    void findByAuthor(int authorId);

    /**
     * Вывод заказов, соответствующих заданным параметрам
     */
    void findByParameter(int authorId, int carId, OrderType type, OrderStatus status);
}