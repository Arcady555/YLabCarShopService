package ru.parfenov.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.enums.CarCondition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CarTest {

    @Test
    @DisplayName("Создание машины")
    public void test_car_initialization() {
        Car car = new Car(1, 100, "Toyota", "Corolla", 2020, 20000, CarCondition.NEW);
        assertEquals(1, car.getId());
        assertEquals(100, car.getOwnerId());
        assertEquals("Toyota", car.getBrand());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYearOfProd());
        assertEquals(20000, car.getPrice());
        assertEquals(CarCondition.NEW, car.getCondition());
    }

    @Test
    @DisplayName("Проверка сеттеров")
    public void test_car_setters() {
        Car car = new Car();
        car.setId(3);
        car.setOwnerId(102);
        car.setBrand("Ford");
        car.setModel("Focus");
        car.setYearOfProd(2018);
        car.setPrice(15000);
        car.setCondition(CarCondition.USED);
        assertEquals(3, car.getId());
        assertEquals(102, car.getOwnerId());
        assertEquals("Ford", car.getBrand());
        assertEquals("Focus", car.getModel());
        assertEquals(2018, car.getYearOfProd());
        assertEquals(15000, car.getPrice());
        assertEquals(CarCondition.USED, car.getCondition());
    }

    @Test
    @DisplayName("Проверка toString()")
    public void test_car_to_string() {
        Car car = new Car(4, 103, "BMW", "X5", 2021, 50000, CarCondition.NEW);
        String expected = "id: 4, ownerId: 103, brand: BMW, model: X5, year of produce: 2021, price: 50000, condition: new.";
        assertEquals(expected, car.toString());
    }

    @Test
    @DisplayName("Пустые значения")
    public void test_car_null_empty_strings() {
        Car car1 = new Car(5, 104, null, null, 2022, 30000, CarCondition.NEW);
        assertNull(car1.getBrand());
        assertNull(car1.getModel());

        Car car2 = new Car(6, 105, "", "", 2022, 30000, CarCondition.NEW);
        assertEquals("", car2.getBrand());
        assertEquals("", car2.getModel());
    }
}