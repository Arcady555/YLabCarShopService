package ru.parfenov.homework_3.store;

import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;

import java.sql.SQLException;

@Testcontainers
class UserStoreJdbcImplTest {
    @Container
    public static InitContainer initContainer;
    private static UserStoreJdbcImpl userStore;

    @BeforeAll
    static void beforeAll() {
        initContainer.getPostgreSQLContainer().start();
    }

    @AfterAll
    static void afterAll() {
        initContainer.getPostgreSQLContainer().stop();
    }

    @BeforeAll
    public static void initConnection() throws Exception {
        userStore = new UserStoreJdbcImpl(initContainer.getConnection());
        User user = new User(0, UserRole.CLIENT, "Arcady", "password", "contact info", 0);
        userStore.create(user);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        initContainer.getConnection().close();
    }

    @Test
    @DisplayName("Проверка findAll()")
    void whenCreateAndGetAllThanOk() {
        Assertions.assertEquals(userStore.findAll().get(0).getId(), 1);
        Assertions.assertEquals(userStore.findAll().get(0).getRole(), UserRole.CLIENT);
        Assertions.assertEquals(userStore.findAll().get(0).getName(), "Arcady");
        Assertions.assertEquals(userStore.findAll().get(0).getPassword(), "password");
        Assertions.assertEquals(userStore.findAll().get(0).getContactInfo(), "contact info");
        Assertions.assertEquals(userStore.findAll().get(0).getBuysAmount(), 0);
    }

    @Test
    @DisplayName("Проверка findAll()")
    void whenCreateAndFindByIdThanOk() {
        Assertions.assertEquals(userStore.findById(1).getRole(), UserRole.CLIENT);
        Assertions.assertEquals(userStore.findById(1).getName(), "Arcady");
        Assertions.assertEquals(userStore.findById(1).getPassword(), "password");
        Assertions.assertEquals(userStore.findById(1).getContactInfo(), "contact info");
        Assertions.assertEquals(userStore.findById(1).getBuysAmount(), 0);
    }
}