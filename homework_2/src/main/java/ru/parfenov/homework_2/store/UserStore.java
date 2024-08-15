package ru.parfenov.homework_2.store;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о пользователях приложения
 */
public interface UserStore {
    /**
     * Создание карточки юзера
     */
    User create(User user);

    /**
     * Поиск юзера по его уникальному ID
     */
    User findById(int id);

    /**
     * Метод предлагает обновление юзера.
     */
    User update(User user);

    /**
     * Удаление карточки юзера
     */
    User delete(User user);

    /**
     * Вывод списка всех юзеров
     */
    List<User> findAll();

    /**
     * Метод предлагает поиск юзеров по указанным параметрам
     */
    List<User> findByParameters(UserRole role, String name, String contactInfo, int buysAmount);
}