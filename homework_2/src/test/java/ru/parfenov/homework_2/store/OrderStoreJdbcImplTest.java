package ru.parfenov.homework_2.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
class OrderStoreJdbcImplTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("y_lab_car_service")
            .withUsername("user")
            .withPassword("pass")
            .withInitScript("src/main/resources/db/changelog/01_ddl_create_tables.xml")
            .withInitScript("src/main/resources/db/changelog/02_dml_insert_admin_into_users.xml");
    private static Connection connection;
    private static OrderStoreJdbcImpl orderStore;

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
        orderStore = new OrderStoreJdbcImpl(connection);
        User user = new User(10, UserRole.CLIENT, "Arcady", "password", "contact info", 0);
        Car car = new Car(11, user.getId(), "brand", "model", 2022, 1000000, CarCondition.NEW);
        Order order = new Order(0, user.getId(), car.getId(), OrderType.BUY, OrderStatus.OPEN);
        orderStore.create(order);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    void whenCreateAndGetAllThanOk() {
        Assertions.assertEquals(orderStore.findAll().get(0).getId(), 1);
        Assertions.assertEquals(orderStore.findAll().get(0).getAuthorId(), 10);
        Assertions.assertEquals(orderStore.findAll().get(0).getCarId(), 11);
        Assertions.assertEquals(orderStore.findAll().get(0).getType(), OrderType.BUY);
        Assertions.assertEquals(orderStore.findAll().get(0).getStatus(), OrderStatus.OPEN);
    }

    @Test
    void whenCreateAndFindByIdThanOk() {
        Assertions.assertEquals(orderStore.findById(1).getAuthorId(), 10);
        Assertions.assertEquals(orderStore.findById(1).getCarId(), 11);
        Assertions.assertEquals(orderStore.findById(1).getType(), OrderType.BUY);
        Assertions.assertEquals(orderStore.findById(1).getStatus(), OrderStatus.OPEN);
    }
}