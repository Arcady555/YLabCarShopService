package ru.parfenov.homework_2.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
class CarStoreJdbcImplTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("y_lab_car_service")
            .withUsername("user")
            .withPassword("pass")
            .withInitScript("src/main/resources/db/changelog/01_ddl_create_tables.xml")
            .withInitScript("src/main/resources/db/changelog/02_dml_insert_admin_into_users.xml");
    private static Connection connection;
    private static CarStoreJdbcImpl carStore;

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
        carStore = new CarStoreJdbcImpl(connection);
        User user = new User(10, UserRole.CLIENT, "Arcady", "password", "contact info", 0);
        Car car = new Car(0, user.getId(), "brand", "model", 2022, 1000000, CarCondition.NEW);
        carStore.create(car);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    void whenCreateAndGetAllThanOk() {
        Assertions.assertEquals(carStore.findAll().get(0).getId(), 1);
        Assertions.assertEquals(carStore.findAll().get(0).getBrand(), "brand");
        Assertions.assertEquals(carStore.findAll().get(0).getModel(), "model");
        Assertions.assertEquals(carStore.findAll().get(0).getYearOfProd(), 2022);
        Assertions.assertEquals(carStore.findAll().get(0).getPrice(), 1000000);
        Assertions.assertEquals(carStore.findAll().get(0).getCondition(), CarCondition.NEW);
    }

    @Test
    void whenCreateAndFindByIdThanOk() {
        Assertions.assertEquals(carStore.findById(1).getBrand(), "brand");
        Assertions.assertEquals(carStore.findById(1).getModel(), "model");
        Assertions.assertEquals(carStore.findById(1).getYearOfProd(), 2022);
        Assertions.assertEquals(carStore.findById(1).getPrice(), 1000000);
        Assertions.assertEquals(carStore.findById(1).getCondition(), CarCondition.NEW);
    }
}