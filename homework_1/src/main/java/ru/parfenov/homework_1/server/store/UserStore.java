package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;

import java.util.List;

public interface UserStore {
    User create(User user);

    User findById(int id);

    User update(User user);

    User delete(User user);

    List<User> findAll();

    /**
     * * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id юзера, его роль, имя, строка(может содержаться в контактной информации), число покупок
     */

    List<User> findByParameters(int id, UserRoles role, String name, String contactInfo, int buysAmount);
}