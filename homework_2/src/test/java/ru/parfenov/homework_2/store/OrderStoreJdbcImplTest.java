package ru.parfenov.homework_2.store;

import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;

import java.sql.SQLException;

@Testcontainers
class OrderStoreJdbcImplTest {
    @Container
    public static InitContainer initContainer;
    private static OrderStoreJdbcImpl orderStore;

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
        orderStore = new OrderStoreJdbcImpl(initContainer.getConnection());
        User user = new User(10, UserRole.CLIENT, "Arcady", "password", "contact info", 0);
        Car car = new Car(11, user.getId(), "brand", "model", 2022, 1000000, CarCondition.NEW);
        Order order = new Order(0, user.getId(), car.getId(), OrderType.BUY, OrderStatus.OPEN);
        orderStore.create(order);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        initContainer.getConnection().close();
    }

    @Test
    @DisplayName("Проверка findAll()")
    void whenCreateAndGetAllThanOk() {
        Assertions.assertEquals(orderStore.findAll().get(0).getId(), 1);
        Assertions.assertEquals(orderStore.findAll().get(0).getAuthorId(), 10);
        Assertions.assertEquals(orderStore.findAll().get(0).getCarId(), 11);
        Assertions.assertEquals(orderStore.findAll().get(0).getType(), OrderType.BUY);
        Assertions.assertEquals(orderStore.findAll().get(0).getStatus(), OrderStatus.OPEN);
    }

    @Test
    @DisplayName("Проверка findById()")
    void whenCreateAndFindByIdThanOk() {
        Assertions.assertEquals(orderStore.findById(1).getAuthorId(), 10);
        Assertions.assertEquals(orderStore.findById(1).getCarId(), 11);
        Assertions.assertEquals(orderStore.findById(1).getType(), OrderType.BUY);
        Assertions.assertEquals(orderStore.findById(1).getStatus(), OrderStatus.OPEN);
    }
}