package ru.parfenov.homework_2.store;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;

import java.util.List;

public interface UserStore {
    User create(User user);

    User findById(int id);

    User update(User user);

    User delete(User user);

    List<User> findAll();

    /**
     * * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * роль юзера, имя, строка(может содержаться в контактной информации), число покупок
     */

    List<User> findByParameters(UserRole role, String name, String contactInfo, int buysAmount);
}