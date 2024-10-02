package ru.homework_5.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework_5.enums.Role;
import ru.homework_5.model.Person;

import java.util.List;

/**
 * Класс передаёт запросы в хранилище данных о пользователях приложения
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    /**
     * Проверка наличия в хранилище юзера с таким ID и паролем
     *
     * @param userId   ID юзера
     * @param password его пароль для входа в систему
     * @return Person - сущность из блока ru/parfenov/homework_3/model.
     */
    Person findByIdAndPassword(int userId, String password);

    /**
     * Вывод списка всех юзеров
     *
     * @return List список всех юзеров
     */
    @Override
    List<Person> findAll();

    /**
     * Метод предлагает поиск юзеров по указанным параметрам
     * Параметры можно указывать не все
     *
     * @param role        роль юзера
     * @param name        имя
     * @param contactInfo контактная информация
     * @param buysAmount  количество покупок
     * @return List список таких юзеров
     */
    @Query("SELECT p FROM Person p WHERE " +
            "(:role IS NULL OR p.role = :role) AND " +
            "(:name = '' OR p.name = :name) AND " +
            "(:contactInfo = '' OR p.contactInfo LIKE CONCAT ('%', :contactInfo, '%')) AND" +
            "(:buysAmount = -1 OR p.buysAmount = :buysAmount)"
    )
    List<Person> findByParameters(Role role, String name, String contactInfo, int buysAmount);
}