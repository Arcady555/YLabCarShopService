package ru.parfenov.homework_3.store;

import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;

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
     * Проверка наличия в хранилище юзера с таким ID и паролем
     */
    User findByIdAndPassword(int userId, String password);

    /**
     * Метод предлагает обновление юзера.
     */
    boolean update(User user);

    /**
     * Удаление карточки юзера
     */
    boolean delete(int userId);

    /**
     * Вывод списка всех юзеров
     */
    List<User> findAll();

    /**
     * Метод предлагает поиск юзеров по указанным параметрам
     */
    List<User> findByParameters(UserRole role, String name, String contactInfo, int buysAmount);
}