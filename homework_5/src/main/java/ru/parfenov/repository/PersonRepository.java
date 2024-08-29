package ru.parfenov.repository;

import ru.parfenov.enums.PersonRole;
import ru.parfenov.model.Person;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о пользователях приложения
 */
public interface PersonRepository {
    /**
     * Создание карточки юзера
     */
    /**
     * Создание карточки юзера
     *
     * @param person Person - сущность из блока ru/parfenov/homework_3/model.
     * @return Person - сущность из блока ru/parfenov/homework_3/model.
     */
    Person create(Person person);

    /**
     * Поиск юзера по его уникальному ID
     *
     * @param id юзера
     * @return Person - сущность из блока ru/parfenov/homework_3/model.
     */
    Person findById(int id);

    /**
     * Проверка наличия в хранилище юзера с таким ID и паролем
     *
     * @param userId   ID юзера
     * @param password его пароль для входа в систему
     * @return Person - сущность из блока ru/parfenov/homework_3/model.
     */
    Person findByIdAndPassword(int userId, String password);

    /**
     * Метод предлагает обновление юзера.
     *
     * @param person Person - сущность из блока ru/parfenov/homework_3/model.
     * @return получилось или нет обновить карточку юзера
     */
    boolean update(Person person);

    /**
     * Удаление карточки юзера
     *
     * @param userId ID юзера
     * @return получилось или нет удалить карточку юзера
     */
    boolean delete(int userId);

    /**
     * Вывод списка всех юзеров
     *
     * @return List список всех юзеров
     */
    List<Person> findAll();

    /**
     * Метод предлагает поиск юзеров по указанным параметрам
     *
     * @param role        роль юзера
     * @param name        имя
     * @param contactInfo контактная информация
     * @param buysAmount  количество покупок
     * @return List список таких юзеров
     */
    List<Person> findByParameters(PersonRole role, String name, String contactInfo, int buysAmount);
}