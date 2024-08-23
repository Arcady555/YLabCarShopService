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
     * @param order Order сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     * @return Order сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Order create(Order order);

    /**
     * Поиск заказа по его уникальному ID
     * @param id ID заказа
     * @return Order сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Order findById(int id);

    /**
     * Метод предлагает обновление заказа.
     * @param orderId ID заказа
     * @return получилось обновить или нет
     */
    boolean update(int orderId);

    /**
     * Удаление карточки заказа
     * @param orderId ID заказа
     * @return получилось удалить или нет
     */
    boolean delete(int orderId);

    /**
     * Вывод списка всех заказов
     * @return List список всех заказов
     */
    List<Order> findAll();

    /**
     * Поиск заказа по его создателю
     * @param authorId ID юзера - создателя заказов
     * @return List список таких заказов
     */
    List<Order> findByAuthor(int authorId);

    /**
     * Метод предлагает поиск заказа по указанным параметрам
     * @param authorId ID юзера-создателя заказа
     * @param carId id машины, полученный при её создании, заведении карточки в БД
     * @param type тип заказа - продажа или сервис
     * @param status статус заказа - открыт или закрыт
     * @return List список таких заказов
     */
    List<Order> findByParameter(int authorId, int carId, OrderType type, OrderStatus status);
}