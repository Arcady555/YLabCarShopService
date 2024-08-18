package ru.parfenov.homework_3.service;

import ru.parfenov.homework_3.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface OrderService {

    /**
     * Метод задействован при создании карточки заказа пользователем
     */
    Optional<Order> create(int authorId, int carId, String type);

    /**
     * Поиск заказа по его ID
     */
    Optional<Order> findById(String id);

    /**
     * Закрытие заказа
     */
    boolean close(String orderId);

    /**
     * Удаление заказа
     */
    boolean delete(String orderId);

    /**
     * Вывод всех заказов
     */
    List<Order> findAll();

    /**
     * Вывод заказов по их создателю
     */
    List<Order> findByAuthor(int authorId);

    /**
     * Вывод заказов, соответствующих заданным параметрам
     */
    List<Order> findByParameter(String authorId, String carId, String type, String status);
}