package ru.parfenov.homework_3.repository;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.utility.JdbcRequests;
import ru.parfenov.homework_3.utility.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserRepositoryJdbcImpl implements UserRepository {
    private final Connection connection;

    public UserRepositoryJdbcImpl() throws Exception {
        this.connection = Utility.loadConnection();
    }

    public UserRepositoryJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                JdbcRequests.createUser,
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
            log.error("Exception in UserRepositoryJdbcImpl.create(). ", e);
        }
        return findById(user.getId());
    }

    @Override
    public User findById(int userId) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findUserById)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = returnUser(resultSet);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.findById(). ", e);
        }
        return user;
    }

    @Override
    public User findByIdAndPassword(int userId, String password) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findUserByIdAndPassword)) {
            statement.setInt(1, userId);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = returnUser(resultSet);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.findByIdAndPassword(). ", e);
        }
        return user;
    }

    /**
     * Запрос в БД формируется из того, какие поля юзера были заполнены для изменения
     */
    @Override
    public boolean update(User user) {
        int id = user.getId();
        UserRole role = user.getRole();
        String name = user.getName();
        String password = user.getPassword();
        String contactInfo = user.getContactInfo();
        int buysAmount = user.getBuysAmount();

        String request = getRequestForUpdate(role, name, password, contactInfo, buysAmount);

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.users SET " + request +
                        " WHERE id = ?")
        ) {
            int i = generateStatementSets(statement, role, name, password, contactInfo, buysAmount);
            i++;
            statement.setInt(i, id);
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.update(). ", e);
        }
        return findById(user.getId()) != null && checkUpdate(user);
    }

    @Override
    public boolean delete(int userId) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.deleteUser)) {
            statement.setInt(1, userId);
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.delete(). ", e);
        }
        return findById(userId) == null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findAllUsers)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = returnUser(resultSet);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.findAll(). ", e);
        }
        return users;
    }

    /**
     * Запрос в БД формируется из того, какие поля юзера были заполнены в качестве параметров поиска
     */
    @Override
    public List<User> findByParameters(UserRole role, String name, String contactInfo, int buysAmount) {
        String request = getRequestForFindByParam(role, name, contactInfo, buysAmount);
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM cs_schema.users WHERE " + request)
        ) {
            generateStatementSets(statement, role, name, "", contactInfo, buysAmount);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = returnUser(resultSet);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.findByParameters(). ", e);
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

    private String getRequestForFindByParam(UserRole role,
                                            String name,
                                            String contactInfo,
                                            int buysAmount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(role != null ? " user_role = ? and" : "").
                append(!name.isEmpty() ? " name = ? and" : "").
                append(!contactInfo.isEmpty() ? " contact_info LIKE %?% and" : "").
                append(buysAmount != 0 ? " buys_amount = ?" : "");
        if (stringBuilder.toString().endsWith("and")) stringBuilder.setLength(stringBuilder.length() - 3);

        return stringBuilder.toString();
    }

    private String getRequestForUpdate(UserRole role,
                                       String name,
                                       String password,
                                       String contactInfo,
                                       int buysAmount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(role != null ? " user_role = ? ," : "").
                append(!name.isEmpty() ? " name = ? ," : "").
                append(!password.isEmpty() ? "password = ? ," : "").
                append(!contactInfo.isEmpty() ? " contact_info = ? ," : "").
                append(buysAmount != 0 ? " buys_amount = ?" : "");
        if (stringBuilder.toString().endsWith(",")) stringBuilder.setLength(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private int generateStatementSets(PreparedStatement statement,
                                      UserRole role,
                                      String name,
                                      String password,
                                      String contactInfo,
                                      int buysAmount) throws SQLException {
        int result = 0;
        if (role != null) {
            result++;
            statement.setString(result, role.toString());
        }
        if (!name.isEmpty()) {
            result++;
            statement.setString(result, name);
        }
        if (!password.isEmpty()) {
            result++;
            statement.setString(result, password);
        }
        if (!contactInfo.isEmpty()) {
            result++;
            statement.setString(result, contactInfo);
        }
        if (buysAmount != 0) {
            result++;
            statement.setInt(result, buysAmount);
        }
        return result;
    }

    private boolean checkUpdate(User user) {
        User userForCheck = findById(user.getId());
        boolean isRole = user.getRole() == null || user.getRole().equals(userForCheck.getRole());
        boolean isName = user.getName() == null || user.getName().equals(userForCheck.getName());
        boolean isPassword = user.getPassword() == null || user.getPassword().equals(userForCheck.getPassword());
        boolean isContact = user.getContactInfo() == null || user.getContactInfo().equals(userForCheck.getContactInfo());
        boolean isBuysAmount = user.getBuysAmount() == 0 || user.getBuysAmount() == userForCheck.getBuysAmount();

        return isRole && isName && isPassword && isContact && isBuysAmount;
    }
}