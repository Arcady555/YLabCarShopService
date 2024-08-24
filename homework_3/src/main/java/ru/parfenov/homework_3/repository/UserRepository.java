package ru.parfenov.homework_3.repository;

import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о пользователях приложения
 */
public interface UserRepository {
    /**
     * Создание карточки юзера
     */
    /**
     * Создание карточки юзера
     * @param user User - сущность из блока ru/parfenov/homework_3/model.
     * @return User - сущность из блока ru/parfenov/homework_3/model.
     */
    User create(User user);

    /**
     * Поиск юзера по его уникальному ID
     * @param id юзера
     * @return User - сущность из блока ru/parfenov/homework_3/model.
     */
    User findById(int id);

    /**
     * Проверка наличия в хранилище юзера с таким ID и паролем
     * @param userId ID юзера
     * @param password его пароль для входа в систему
     * @return User - сущность из блока ru/parfenov/homework_3/model.
     */
    User findByIdAndPassword(int userId, String password);

    /**
     * Метод предлагает обновление юзера.
     * @param user User - сущность из блока ru/parfenov/homework_3/model.
     * @return получилось или нет обновить карточку юзера
     */
    boolean update(User user);

    /**
     * Удаление карточки юзера
     * @param userId ID юзера
     * @return получилось или нет удалить карточку юзера
     */
    boolean delete(int userId);

    /**
     * Вывод списка всех юзеров
     * @return List список всех юзеров
     */
    List<User> findAll();

    /**
     * Метод предлагает поиск юзеров по указанным параметрам
     * @param role роль юзера
     * @param name имя
     * @param contactInfo контактная информация
     * @param buysAmount количество покупок
     * @return List список таких юзеров
     */
    List<User> findByParameters(UserRole role, String name, String contactInfo, int buysAmount);
}