package ru.parfenov.service.impl;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.enums.CarCondition;
import ru.parfenov.model.Car;
import ru.parfenov.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarServiceServletImplTest {
    CarRepository repo = mock(CarRepository.class);
    CarServiceServletImpl service = new CarServiceServletImpl(repo);

    @Test
    @DisplayName("Проверка метода Create")
    public void create_car_with_valid_inputs() {
        Car car = new Car(0, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(repo.create(any(Car.class))).thenReturn(car);
        Optional<Car> result = service.create(1, "Toyota", "Camry", 2020, 30000, "new");
        assertTrue(result.isPresent());
        assertEquals(car, result.get());
    }

    @Test
    @DisplayName("Проверка метода FindById")
    public void find_car_by_id_exists() {
        Car car = new Car(1, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(repo.findById(1)).thenReturn(car);
        Optional<Car> result = service.findById(1);
        assertTrue(result.isPresent());
        assertEquals(car, result.get());
    }

    @Test
    @DisplayName("Проверка метода FindBtOwnerId")
    public void find_cars_by_owner_id() {
        List<Car> cars = List.of(new Car(1, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW));
        when(repo.findByOwner(1)).thenReturn(cars);
        List<Car> result = service.findByOwner(1);
        assertEquals(cars, result);
    }

    @Test
    @DisplayName("Проверка метода isOwnCar")
    public void check_car_ownership_by_user_id() {
        Car car = new Car(1, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(repo.findById(1)).thenReturn(car);
        boolean result = service.isOwnCar(1, 1);
        assertTrue(result);
    }

    @Test
    @DisplayName("Создание машины с невалидным Condition")
    public void create_car_with_invalid_condition_string() {
        Optional<Car> result = service.create(
                1, "Toyota", "Camry", 2020, 30000, "invalid"
        );
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Поиск машины с несуществующим ID")
    public void find_car_by_non_existent_id() {
        when(repo.findById(999)).thenReturn(null);
        Optional<Car> result = service.findById(999);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Проверка метода isOwnCar c машиной с несуществующим ID")
    public void check_car_ownership_when_car_does_not_exist() {
        when(repo.findById(999)).thenReturn(null);
        boolean result = service.isOwnCar(1, 999);
        assertFalse(result);
    }

    @Test
    @DisplayName("Update машины с невалидным Condition")
    public void update_car_with_invalid_condition_string() {
        boolean result = service.update(1, 1, "Toyota", "Camry", 2020, 30000, "invalid");
        assertFalse(result);
    }
}