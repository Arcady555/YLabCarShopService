package ru.parfenov.homework_1.server.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.store.CarStore;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса машин")
public class CarServiceConsoleImplTest {

    @Test
    @DisplayName("Создание машины")
    public void create_car_with_valid_parameters() {
        CarStore store = mock(CarStore.class);
        CarServiceConsoleImpl service = new CarServiceConsoleImpl(store);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Car car = new Car(0, user, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);

        when(store.create(any(Car.class))).thenReturn(car);

        Car result = service.create(user, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);

        assertNotNull(result);
        assertEquals("Toyota", result.getBrand());
        verify(store).create(any(Car.class));
    }

    @Test
    @DisplayName("Поиск машины по ID")
    public void find_car_by_valid_id() {
        CarStore store = mock(CarStore.class);
        CarServiceConsoleImpl service = new CarServiceConsoleImpl(store);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Car car = new Car(1, user, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);

        when(store.findById(1)).thenReturn(car);

        Car result = service.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(store).findById(1);
    }

    @Test
    @DisplayName("Поиск машины по собственнику")
    public void find_cars_by_valid_user_id() {
        CarStore store = mock(CarStore.class);
        CarServiceConsoleImpl service = new CarServiceConsoleImpl(store);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        List<Car> cars = List.of(
                new Car(1, user, "Toyota", "Camry", 2020, 30000, CarCondition.NEW),
                new Car(2, user, "Honda", "Civic", 2019, 25000, CarCondition.USED)
        );

        when(store.findByUser(1)).thenReturn(cars);

        List<Car> result = service.findByUser(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(store).findByUser(1);
    }

    @Test
    @DisplayName("Поиск машины по несуществующему ID")
    public void find_car_by_invalid_id() {
        CarStore store = mock(CarStore.class);
        CarServiceConsoleImpl service = new CarServiceConsoleImpl(store);

        when(store.findById(999)).thenReturn(null);

        Car result = service.findById(999);

        assertNull(result);
        verify(store).findById(999);
    }

    @Test
    @DisplayName("Поиск машины в пустом списке")
    public void find_cars_by_user_id_with_no_cars() {
        CarStore store = mock(CarStore.class);
        CarServiceConsoleImpl service = new CarServiceConsoleImpl(store);

        when(store.findByUser(2)).thenReturn(Collections.emptyList());

        List<Car> result = service.findByUser(2);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(store).findByUser(2);
    }

    @Test
    @DisplayName("Обновление несуществующей машины")
    public void update_non_existent_car() {
        CarStore store = mock(CarStore.class);
        CarServiceConsoleImpl service = new CarServiceConsoleImpl(store);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Car car = new Car(999, user, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);

        doThrow(new IllegalArgumentException("Car not found")).when(store).update(car);

        assertThrows(IllegalArgumentException.class, () -> {
            service.update(car);
        });

        verify(store).update(car);
    }
}