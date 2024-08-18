package ru.parfenov.homework_3.service;

import ru.parfenov.homework_3.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface UserService {

    /**
     * Метод задействован при создании карточки юзера админом
     */
    Optional<User> createByAdmin(int id, String userRole, String name, String password, String contactInfo, int buysAmount);

    /**
     * Метод задействован при самостоятельно регистрации юзера
     */
    Optional<User> createByReg(String name, String password, String contactInfo);

    /**
     * Поиск юзера по его ID
     */
    Optional<User> findById(String userId);

    /**
     * Обновление данных о юзере
     */
    boolean update(int userId, String userRole, String name, String password, String contactInfo, int buysAmount);

    /**
     * Удаление карточки юзера
     */
    boolean delete(String userId);

    /**
     * Вывод списка всех юзеров
     */
    List<User> findAll();

    /**
     * Поиск юзера по заданным параметрам
     */
    List<User> findByParameters(String role, String name, String contactInfo, String buysAmount);
}