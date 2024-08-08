package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;

public interface UserService {
    void createByAdmin(int id, UserRoles role, String name, String password, String contactInfo, int buysAmount);

    User createByReg(String name, String password, String contactInfo);

    User findById(int id);

    User findByIdForAdmin(int id);

    void update(User user);

    User delete(User user);

    void findAll();

    void findByParameters(int id, UserRoles role, String name, String contactInfo, int buysAmount);
}