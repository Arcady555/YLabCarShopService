package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStoreConsoleImpl implements UserStore {
    private static int userId = 0;

    private final Map<Integer, User> userMap = new HashMap<>();

    public UserStoreConsoleImpl() {
        User user = new User("admin", Utility.adminPassword);
        user.setRole(UserRoles.ADMIN);
        create(user);
        Utility.logging(user.getId(), "registration");
    }

    @Override
    public User create(User user) {
        userId++;
        user.setId(userId);
        userMap.put(userId, user);
        return user;
    }

    @Override
    public User findById(int id) {
        return userMap.get(id);
    }

    @Override
    public User update(User user) {
        return userMap.replace(user.getId(), user);
    }

    @Override
    public User delete(User user) {
        return userMap.remove(user.getId());
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        for (Map.Entry<Integer, User> element : userMap.entrySet()) {
            list.add(element.getValue());
        }
        return list;
    }

    @Override
    public List<User> findByParameters(int id, UserRoles role, String name, String contactInfo, int buysAmount) {
        List<User> allUsers = findAll();
        List<User> result = new ArrayList<>();
        for (User user : allUsers) {
            if (select(user, id, role, name, contactInfo, buysAmount)) {
                result.add(user);
            }
        }
        return result;
    }

    private boolean select(User user, int id, UserRoles role, String name, String contactInfo, int buysAmount) {
        boolean result = id == 0 || user.getId() == id;
        if (role != null && user.getRole() != role) {
            result = false;
        }
        if (!name.isEmpty() && !user.getName().contains(name)) {
            result = false;
        }
        if (!contactInfo.isEmpty() && !user.getContactInfo().contains(contactInfo)) {
            result = false;
        }
        if (buysAmount != 0 && user.getBuysAmount() <= buysAmount) {
            result = false;
        }
        return result;
    }
}