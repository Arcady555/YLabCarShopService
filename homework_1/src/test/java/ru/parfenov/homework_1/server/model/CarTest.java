package ru.parfenov.homework_1.server.model;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.enums.UserRoles;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    public void test_create_car_with_valid_parameters() {
        User owner = new User(1, UserRoles.CLIENT, "John Doe", "password123", "john.doe@example.com", 5);
        Car car = new Car(1, owner, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);

        assertEquals(1, car.getId());
        assertEquals(owner, car.getOwner());
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals(2020, car.getYearOfProd());
        assertEquals(20000, car.getPrice());
        assertEquals(CarCondition.NEW, car.getCondition());
    }

    @Test
    public void test_create_car_with_null_owner() {
        Car car = new Car(2, null, "Honda", "Civic", 2019, 15000, CarCondition.USED);

        assertEquals(2, car.getId());
        assertNull(car.getOwner());
        assertEquals("Honda", car.getBrand());
        assertEquals("Civic", car.getModel());
        assertEquals(2019, car.getYearOfProd());
        assertEquals(15000, car.getPrice());
        assertEquals(CarCondition.USED, car.getCondition());
    }

    @Test
    public void test_get_id_valid_car() {
        User owner = new User(1, UserRoles.CLIENT, "John Doe", "password123", "john.doe@example.com", 5);
        Car car = new Car(1, owner, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        assertEquals(1, car.getId());
    }
}