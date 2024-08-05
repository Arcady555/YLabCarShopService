package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.store.UserStore;
import ru.parfenov.homework_1.server.utility.Utility;

import java.util.List;

public class UserServiceConsoleImpl implements UserService {
    private final UserStore store;

    public UserServiceConsoleImpl(UserStore store) {
        this.store = store;
    }

    @Override
    public void createByAdmin(int id, UserRoles role, String name, String password, String contactInfo, int buysAmount) {
        User user = store.create(new User(0, role, name, password, contactInfo, buysAmount));
        Utility.printUser(user);
    }

    @Override
    public User createByReg(String name, String password, String contactInfo) {
        User user = store.create(new User(0, UserRoles.CLIENT, name, password, contactInfo, 0));
        Utility.printUser(user);
        return user;
    }

    @Override
    public User findById(int id) {
        User user = store.findById(id);
        if (user == null) {
            System.out.println("User not found!");
        } else {
            Utility.printUser(user);
        }
        return user;
    }

    @Override
    public User findByIdForAdmin(int id) {
        User user = store.findById(id);
        if (user == null) {
            System.out.println("User not found!");
        } else {
            System.out.println(
                    "id: " + user.getId() + ", " +
                            "role: " + user.getRole() + ", " +
                            "name: " + user.getName() + ", " +
                            "password: " + user.getPassword() + ", " +
                            "contact info: " + user.getContactInfo() + ", " +
                            "buy amount: " + user.getBuysAmount() + "."
            );
        }
        return user;
    }

    @Override
    public void update(User user) {
        store.update(user);
        Utility.printUser(user);
    }

    @Override
    public User delete(User user) {
        return store.delete(user);
    }

    @Override
    public void findAll() {
        for (User user : store.findAll()) {
            Utility.printUser(user);
        }
    }

    @Override
    public void findByParameters(int id, UserRoles role, String name, String contactInfo, int buysAmount) {
        List<User> list = store.findByParameters(id, role, name, contactInfo, buysAmount);
        for (User user : list) {
            Utility.printUser(user);
        }
    }
}