package ru.parfenov.homework_1.server.store;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarStoreConsoleImplTest {

    @Test
    public void test_find_by_user() {
        CarStoreConsoleImpl store = new CarStoreConsoleImpl();
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Car car1 = new Car(0, user, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        Car car2 = new Car(0, user, "Honda", "Civic", 2019, 18000, CarCondition.USED);
        store.create(car1);
        store.create(car2);
        List<Car> cars = store.findByUser(1);
        assertEquals(2, cars.size());
    }

    @Test
    public void test_create_car_with_existing_id() {
        CarStoreConsoleImpl store = new CarStoreConsoleImpl();
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Car car1 = new Car(0, user, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        store.create(car1);
        Car car2 = new Car(1, user, "Honda", "Civic", 2019, 18000, CarCondition.USED);
        store.create(car2);
        assertEquals(2, store.findAll().size());
    }

    @Test
    public void test_find_by_non_existent_id() {
        CarStoreConsoleImpl store = new CarStoreConsoleImpl();
        Car foundCar = store.findById(999);
        assertNull(foundCar);
    }

    @Test
    public void test_find_by_user_with_no_cars() {
        CarStoreConsoleImpl store = new CarStoreConsoleImpl();
        List<Car> cars = store.findByUser(999);
        assertTrue(cars.isEmpty());
    }

    @Test
    public void test_update_non_existent_car() {
        CarStoreConsoleImpl store = new CarStoreConsoleImpl();
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Car car = new Car(999, user, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        Car updatedCar = store.update(car);
        assertNull(updatedCar);
    }
}