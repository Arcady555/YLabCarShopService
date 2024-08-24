package ru.parfenov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.enums.UserRole;
import ru.parfenov.model.User;
import ru.parfenov.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceServletImpl implements UserService, GettingIntFromString {
    private final UserRepository repo;

    @Override
    public Optional<User> createByAdmin(
            int id, String roleStr, String name, String password, String contactInfo, int buysAmount
    ) {
        UserRole role = getUserRoleFromString(roleStr);
        return Optional.ofNullable(repo.create(new User(0, role, name, password, contactInfo, buysAmount)));
    }

    @Override
    public Optional<User> createByReg(String name, String password, String contactInfo) {
        return Optional.ofNullable(
                repo.create(new User(0, UserRole.CLIENT, name, password, contactInfo, 0))
        );
    }

    @Override
    public Optional<User> findById(String userIdStr) {
        int userId = getIntFromString(userIdStr);
        return Optional.ofNullable(repo.findById(userId));
    }

    @Override
    public Optional<User> findByIdAndPassword(int userId, String password) {
        return Optional.ofNullable(repo.findByIdAndPassword(userId, password));
    }

    @Override
    public boolean update(int userId, String roleStr, String name, String password, String contactInfo, int buysAmount) {
        UserRole role = getUserRoleFromString(roleStr);
        User user = new User(userId, role, name, password, contactInfo, buysAmount);
        return repo.update(user);
    }

    @Override
    public boolean delete(String userIdStr) {
        int userId = getIntFromString(userIdStr);
        return repo.delete(userId);
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public List<User> findByParameters(String roleStr, String name, String contactInfo, String buysAmountStr) {
        UserRole role = getUserRoleFromString(roleStr);
        int buysAmount = getIntFromString(buysAmountStr);
        return repo.findByParameters(role, name, contactInfo, buysAmount);
    }

    private UserRole getUserRoleFromString(String roleStr) {
        return "client".equals(roleStr) ? UserRole.CLIENT :
                ("manager".equals(roleStr) ? UserRole.MANAGER :
                        ("admin".equals(roleStr) ? UserRole.ADMIN :
                                null));
    }
}