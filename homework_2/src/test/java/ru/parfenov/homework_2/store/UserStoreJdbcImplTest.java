package ru.parfenov.homework_2.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
class UserStoreJdbcImplTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("y_lab_car_service")
            .withUsername("user")
            .withPassword("pass")
            .withInitScript("src/main/resources/db/changelog/01_ddl_create_tables.xml")
            .withInitScript("src/main/resources/db/changelog/02_dml_insert_admin_into_users.xml");
    private static Connection connection;
    private static UserStoreJdbcImpl userStore;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeAll
    public static void initConnection() throws Exception {
        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
        userStore = new UserStoreJdbcImpl(connection);
        User user = new User(0, UserRole.CLIENT, "Arcady", "password", "contact info", 0);
        userStore.create(user);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    void whenCreateAndGetAllThanOk() {
        Assertions.assertEquals(userStore.findAll().get(0).getId(), 1);
        Assertions.assertEquals(userStore.findAll().get(0).getRole(), UserRole.CLIENT);
        Assertions.assertEquals(userStore.findAll().get(0).getName(), "Arcady");
        Assertions.assertEquals(userStore.findAll().get(0).getPassword(), "password");
        Assertions.assertEquals(userStore.findAll().get(0).getContactInfo(), "contact info");
        Assertions.assertEquals(userStore.findAll().get(0).getBuysAmount(), 0);
    }

    @Test
    void whenCreateAndFindByIdThanOk() {
        Assertions.assertEquals(userStore.findById(1).getRole(), UserRole.CLIENT);
        Assertions.assertEquals(userStore.findById(1).getName(), "Arcady");
        Assertions.assertEquals(userStore.findById(1).getPassword(), "password");
        Assertions.assertEquals(userStore.findById(1).getContactInfo(), "contact info");
        Assertions.assertEquals(userStore.findById(1).getBuysAmount(), 0);
    }
}