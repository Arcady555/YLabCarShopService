package ru.parfenov.homework_2.pages.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateCarPageTest {
    User user = new User(1, UserRole.CLIENT, "John Doe", "password", "contact", 0);
    CarService carService = mock(CarService.class);
    LogService logService = mock(LogService.class);
    CreateCarPage createCarPage = new CreateCarPage(user, carService, logService);
    BufferedReader reader = mock(BufferedReader.class);


    @Test
    @DisplayName("Создание машины с валидными данными")
    public void test_user_creates_car_with_valid_inputs() throws IOException, InterruptedException {
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("Toyota", "Camry", "2020", "30000", "0");
        Car car = new Car(1, user.getId(), "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(carService.create(anyInt(), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        verify(carService).create(user.getId(), "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(user.getId()), contains("create the car with ID:1"));
    }

    @Test
    @DisplayName("Создание машины с валидным ID")
    public void test_car_service_creates_car_with_valid_id() throws IOException, InterruptedException {
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("Toyota", "Camry", "2020", "30000", "0");
        Car car = new Car(1, user.getId(), "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(carService.create(anyInt(), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        assertEquals(1, car.getId());
    }

    @Test
    @DisplayName("Вызов лога")
    public void test_log_service_logs_creation_of_car() throws IOException, InterruptedException {
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("Toyota", "Camry", "2020", "30000", "0");
        Car car = new Car(1, user.getId(), "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(carService.create(anyInt(), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(user.getId()), contains("create the car with ID:1"));
    }
}