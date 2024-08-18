package ru.parfenov.homework_3.service;

import lombok.AllArgsConstructor;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.store.UserStore;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceServletImpl implements UserService, GettingIntFromString {
    private final UserStore store;

    @Override
    public Optional<User> createByAdmin(
            int id, String roleStr, String name, String password, String contactInfo, int buysAmount
    ) {
        UserRole role = getUserRoleFromString(roleStr);
        return Optional.ofNullable(store.create(new User(0, role, name, password, contactInfo, buysAmount)));
    }

    @Override
    public Optional<User> createByReg(String name, String password, String contactInfo) {
        return Optional.ofNullable(
                store.create(new User(0, UserRole.CLIENT, name, password, contactInfo, 0))
        );
    }

    @Override
    public Optional<User> findById(String userIdStr) {
        int userId = getIntFromString(userIdStr);
        return Optional.ofNullable(store.findById(userId));
    }

    @Override
    public Optional<User> findByIdAndPassword(int userId, String password) {
        return Optional.ofNullable(store.findByIdAndPassword(userId, password));
    }

    @Override
    public boolean update(int userId, String roleStr, String name, String password, String contactInfo, int buysAmount) {
        UserRole role = getUserRoleFromString(roleStr);
        User user = new User(userId, role, name, password, contactInfo, buysAmount);
        return store.update(user);
    }

    @Override
    public boolean delete(String userIdStr) {
        int userId = getIntFromString(userIdStr);
        return store.delete(userId);
    }

    @Override
    public List<User> findAll() {
        return store.findAll();
    }

    @Override
    public List<User> findByParameters(String roleStr, String name, String contactInfo, String buysAmountStr) {
        UserRole role = getUserRoleFromString(roleStr);
        int buysAmount = getIntFromString(buysAmountStr);
        return store.findByParameters(role, name, contactInfo, buysAmount);
    }

    private UserRole getUserRoleFromString(String roleStr) {
        return "client".equals(roleStr) ? UserRole.CLIENT :
                ("manager".equals(roleStr) ? UserRole.MANAGER :
                        ("admin".equals(roleStr) ? UserRole.ADMIN :
                                null));
    }
}