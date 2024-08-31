package ru.parfenov.service;

import ru.parfenov.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface OrderService {

    /**
     * Метод задействован при создании карточки заказа пользователем
     *
     * @param authorId ID юзера-создателя заказа
     * @param carId    id машины, полученный при её создании, заведении карточки в БД
     * @param type     тип заказа - продажа или сервис
     * @return Order сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Order> create(int authorId, int carId, String type);

    /**
     * Поиск заказа по его ID
     *
     * @param id ID заказа
     * @return Order сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Order> findById(int id);

    /**
     * Является ли юзер автором заказа
     * Два метода, во втором ID заказа в String
     *
     * @param authorId ID юзера - вероятного автора заказа
     * @param orderId  ID заказа
     * @return true или false
     */
    boolean isOwnOrder(int authorId, String orderId);

    boolean isOwnOrder(int authorId, int orderId);

    /**
     * Закрытие заказа
     *
     * @param orderId ID заказа
     * @return получилось закрыть заказ или нет
     */
    boolean close(int orderId);

    /**
     * Удаление заказа
     *
     * @param orderId ID заказа
     * @return получилось удалить заказ или нет
     */
    boolean delete(int orderId);

    /**
     * Вывод всех заказов
     *
     * @return List список всех заказов
     */
    List<Order> findAll();

    /**
     * Вывод заказов по их создателю
     *
     * @param authorId ID юзера - создателя заказов
     * @return List список таких заказов
     */
    List<Order> findByAuthorId(int authorId);

    /**
     * Вывод заказов, соответствующих заданным параметрам
     *
     * @param authorId ID юзера-создателя заказа
     * @param carId    id машины, полученный при её создании, заведении карточки в БД
     * @param type     тип заказа - продажа или сервис
     * @param status   статус заказа - открыт или закрыт
     * @return List список таких заказов
     */
    List<Order> findByParameter(String authorId, String carId, String type, String status);
}