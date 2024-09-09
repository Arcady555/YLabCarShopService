package ru.parfenov.repository;

import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.parfenov.enums.CarCondition;
import ru.parfenov.enums.UserRole;
import ru.parfenov.model.Car;
import ru.parfenov.model.User;
import ru.parfenov.repository.impl.CarRepositoryJdbcImpl;

import java.sql.SQLException;

@Testcontainers
class CarRepositoryJdbcImplTest {
    @Container
    public static InitContainer initContainer;

    private static CarRepositoryJdbcImpl carStore;

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
        carStore = new CarRepositoryJdbcImpl(initContainer.getConnection());
        User user = new User(10, UserRole.CLIENT, "Arcady", "password", "contact info", 0);
        Car car = new Car(0, user.getId(), "brand", "model", 2022, 1000000, CarCondition.NEW);
        carStore.create(car);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        initContainer.getConnection().close();
    }

    @Test
    @DisplayName("Проверка findAll()")
    void whenCreateAndGetAllThanOk() {
        Car car = carStore.findAll().get(0);
        Assertions.assertNotNull(car);
        Assertions.assertEquals(car.getId(), 1);
        Assertions.assertEquals(car.getOwnerId(), 10);
        Assertions.assertEquals(car.getBrand(), "brand");
        Assertions.assertEquals(car.getModel(), "model");
        Assertions.assertEquals(car.getYearOfProd(), 2022);
        Assertions.assertEquals(car.getPrice(), 1000000);
        Assertions.assertEquals(car.getCondition(), CarCondition.NEW);
    }

    @Test
    @DisplayName("Проверка findById()")
    void whenCreateAndFindByIdThanOk() {
        Car car = carStore.findById(1);
        Assertions.assertNotNull(car);
        Assertions.assertEquals(car.getOwnerId(), 10);
        Assertions.assertEquals(car.getBrand(), "brand");
        Assertions.assertEquals(car.getModel(), "model");
        Assertions.assertEquals(car.getYearOfProd(), 2022);
        Assertions.assertEquals(car.getPrice(), 1000000);
        Assertions.assertEquals(car.getCondition(), CarCondition.NEW);
    }
}