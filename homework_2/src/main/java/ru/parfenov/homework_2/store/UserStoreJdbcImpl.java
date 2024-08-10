package ru.parfenov.homework_2.store;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.utility.Utility;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserStoreJdbcImpl implements UserStore {
    private final Connection connection;

    public UserStoreJdbcImpl() throws Exception {
        InputStream in = UserStoreJdbcImpl.class.getClassLoader()
                .getResourceAsStream("db/liquibase.properties");
        this.connection = Utility.loadConnection(in);
    }

    public UserStoreJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO cs_schema.users(" +
                        "user_role," +
                        " name," +
                        " password," +
                        " contact_info," +
                        " buys_amount" +
                        ")" +
                        " VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getRole().toString());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getContactInfo());
            statement.setInt(5, user.getBuysAmount());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserStoreJdbcImpl.create(). ", e);
        }
        return user;
    }

    @Override
    public User findById(int userId) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.users WHERE id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = returnUser(resultSet);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserStoreJdbcImpl.findById(). ", e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.users SET user_role = ?," +
                        " name = ?," +
                        " password = ?," +
                        " contact_info = ?," +
                        " buys_amount = ?" +
                        " WHERE id = ?")
        ) {
            statement.setString(1, user.getRole().toString());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getContactInfo());
            statement.setInt(5, user.getBuysAmount());
            statement.setInt(6, user.getId());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in UserStoreJdbcImpl.update(). ", e);
        }
        return user;
    }

    @Override
    public User delete(User user) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE cs_schema.users delete WHERE id = ?")) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in UserStoreJdbcImpl.delete(). ", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = returnUser(resultSet);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserStoreJdbcImpl.findAll(). ", e);
        }
        return users;
    }

    /**
     * Метод предполагает поиск по параметрам (всем или некоторые можно не указать)
     * id юзера, его роль, имя, строка(может содержаться в контактной информации), число покупок
     */

    public List<User> findByParameters(int id, UserRole role, String name, String contactInfo, int buysAmount) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM cs_schema.users WHERE id = ?" +
                        " user_role = ?" +
                        " name = ?," +
                        " contact_info = %?%," +
                        " buys_amount = ?")
        ) {
            statement.setInt(1, id);
            statement.setString(2, role.toString());
            statement.setString(3, name);
            statement.setString(4, contactInfo);
            statement.setInt(5, buysAmount);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = returnUser(resultSet);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserStoreJdbcImpl.findByParameters(). ", e);
        }
        return users;
    }

    private User returnUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                getUserRoleFromString(resultSet.getString("user_role")),
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getString("contact_info"),
                resultSet.getInt("buys_amount"));
    }

    private UserRole getUserRoleFromString(String str) {
        UserRole result;
        if ("admin".equals(str)) {
            result = UserRole.ADMIN;
        } else if ("manager".equals(str)) {
            result = UserRole.MANAGER;
        } else {
            result = UserRole.CLIENT;
        }
        return result;
    }
}