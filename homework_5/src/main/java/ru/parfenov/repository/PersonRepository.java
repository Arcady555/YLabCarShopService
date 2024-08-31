package ru.parfenov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.parfenov.enums.PersonRole;
import ru.parfenov.model.Person;

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
     *
     * @param role        роль юзера
     * @param name        имя
     * @param contactInfo контактная информация
     * @param buysAmount  количество покупок
     * @return List список таких юзеров
     */
    @Query(
            "from Person u where u.role = ?1" +
                    " and u.name = ?2" +
                    " and u.contactInfo LIKE %?3%" +
                    " and u.buysAmount = ?4"
    )
    List<Person> findByParameters(PersonRole role, String name, String contactInfo, int buysAmount);
}