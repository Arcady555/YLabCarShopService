package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CreateCarPageTest {

    @Test
    public void test_create_new_car_with_valid_inputs() throws IOException, InterruptedException {
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        CarService carService = mock(CarService.class);
        CreateCarPage createCarPage = new CreateCarPage(user, carService);
        BufferedReader reader = mock(BufferedReader.class);
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("Toyota", "Corolla", "2020", "20000", "0");
        Car car = new Car(1, user, "Toyota", "Corolla", 2020, 20000, CarCondition.NEW);
        when(carService.create(any(User.class), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        verify(carService).create(user, "Toyota", "Corolla", 2020, 20000, CarCondition.NEW);
    }

    @Test
    public void test_create_used_car_with_valid_inputs() throws IOException, InterruptedException {
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        CarService carService = mock(CarService.class);
        CreateCarPage createCarPage = new CreateCarPage(user, carService);
        BufferedReader reader = mock(BufferedReader.class);
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("Honda", "Civic", "2015", "15000", "1");
        Car car = new Car(2, user, "Honda", "Civic", 2015, 15000, CarCondition.USED);
        when(carService.create(any(User.class), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        verify(carService).create(user, "Honda", "Civic", 2015, 15000, CarCondition.USED);
    }

    @Test
    public void test_car_creation_logs_user_action() throws IOException, InterruptedException {
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        CarService carService = mock(CarService.class);
        CreateCarPage createCarPage = new CreateCarPage(user, carService);
        BufferedReader reader = mock(BufferedReader.class);
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("Ford", "Focus", "2018", "18000", "0");
        Car car = new Car(3, user, "Ford", "Focus", 2018, 18000, CarCondition.NEW);
        when(carService.create(any(User.class), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        verify(carService).create(user, "Ford", "Focus", 2018, 18000, CarCondition.NEW);
    }

    @Test
    public void test_car_creation_displays_correct_id() throws IOException, InterruptedException {
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        CarService carService = mock(CarService.class);
        CreateCarPage createCarPage = new CreateCarPage(user, carService);
        BufferedReader reader = mock(BufferedReader.class);
        createCarPage.reader = reader;

        when(reader.readLine()).thenReturn("BMW", "X5", "2021", "50000", "0");
        Car car = new Car(4, user, "BMW", "X5", 2021, 50000, CarCondition.NEW);
        when(carService.create(any(User.class), anyString(), anyString(), anyInt(), anyInt(), any(CarCondition.class))).thenReturn(car);

        createCarPage.run();

        verify(carService).create(user, "BMW", "X5", 2021, 50000, CarCondition.NEW);
    }
}