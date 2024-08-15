package ru.parfenov.homework_3.service;

import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface UserService {

    /**
     * Метод задействован при создании карточки юзера админом
     */
    User createByAdmin(int id, UserRole role, String name, String password, String contactInfo, int buysAmount);

    /**
     * Метод задействован при самостоятельно регистрации юзера
     */
    User createByReg(String name, String password, String contactInfo);

    /**
     * Поиск юзера по его ID
     */
    User findById(int id);

    /**
     * В отличие от обычного поиска юзера по ID тут еще выводится на дисплей пароль юзера
     */
    User findByIdForAdmin(int id);

    /**
     * Обновление данных о юзере
     */
    void update(int userId, UserRole userRole, String name, String password, String contactInfo, int buysAmount);

    /**
     * Удаление карточки юзера
     */
    User delete(int userId);

    /**
     * Вывод списка всех юзеров
     */
    void findAll();

    /**
     * Поиск юзера по заданным параметрам
     */
    void findByParameters(UserRole role, String name, String contactInfo, int buysAmount);
}