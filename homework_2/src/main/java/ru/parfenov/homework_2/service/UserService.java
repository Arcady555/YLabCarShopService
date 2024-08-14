package ru.parfenov.homework_2.service;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;

public interface UserService {
    User createByAdmin(int id, UserRole role, String name, String password, String contactInfo, int buysAmount);

    User createByReg(String name, String password, String contactInfo);

    User findById(int id);

    User findByIdForAdmin(int id);

    void update(int userId, UserRole userRole, String name, String password, String contactInfo, int buysAmount);

    User delete(int userId);

    void findAll();

    void findByParameters(UserRole role, String name, String contactInfo, int buysAmount);
}