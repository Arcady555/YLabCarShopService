package ru.parfenov.service;

import ru.parfenov.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface PersonService {

    /**
     * Метод задействован при создании карточки юзера админом
     *
     * @param id          ID юзера
     * @param userRole    роль юзера
     * @param name        его имя
     * @param password    пароль для входа в систему
     * @param contactInfo контактная информация
     * @param buysAmount  количество совершенных покупок машин
     * @return Person - сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Person> createByAdmin(int id, String userRole, String name, String password, String contactInfo, int buysAmount);

    /**
     * Метод задействован при самостоятельной регистрации юзера
     *
     * @param name        имя
     * @param password    пароль для входа в систему
     * @param contactInfo контактная информация
     * @return Person - сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Person> createByReg(String name, String password, String contactInfo);

    /**
     * Поиск юзера по его ID
     *
     * @param userId ID юзера
     * @return Person - сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Person> findById(int userId);

    /**
     * Проверяет наличие в хранилище юзера с таким ID b паролем. При аутентификации
     *
     * @param userId   ID юзера
     * @param password пароль для входа в систему
     * @return Person - сущность из блока ru/parfenov/homework_3/model. Обёрнут в Optional
     */
    Optional<Person> findByIdAndPassword(int userId, String password);

    /**
     * Обновление данных о юзере
     *
     * @param userId      ID юзера
     * @param userRole    роль юзера
     * @param name        его имя
     * @param password    пароль для входа в систему
     * @param contactInfo контактная информация
     * @param buysAmount  количество совершенных покупок машин
     * @return получилось обновить юзера или нет
     */
    boolean update(int userId, String userRole, String name, String password, String contactInfo, int buysAmount);

    /**
     * Удаление карточки юзера
     *
     * @param userId ID юзера
     * @return получилось удалить юзера или нет
     */
    boolean delete(int userId);

    /**
     * Вывод списка всех юзеров
     *
     * @return List список всех юзеров
     */
    List<Person> findAll();

    /**
     * Поиск юзера по заданным параметрам
     *
     * @param role        роль юзера
     * @param name        имя
     * @param contactInfo контактная информация
     * @param buysAmount  количество покупок
     * @return List список таких юзеров
     */
    List<Person> findByParameters(String role, String name, String contactInfo, String buysAmount);
}