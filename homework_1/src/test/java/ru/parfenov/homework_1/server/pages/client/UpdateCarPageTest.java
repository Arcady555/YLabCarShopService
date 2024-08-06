package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

public class UpdateCarPageTest {
    @Test
    public void admin_can_update_any_car_details() throws IOException, InterruptedException {
        User admin = new User(1, UserRoles.ADMIN, "admin", "password", "contact", 0);
        CarService carService = Mockito.mock(CarService.class);
        Car car = new Car(1, admin, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        Mockito.when(carService.findById(1)).thenReturn(car);
        UpdateCarPage updateCarPage = new UpdateCarPage(admin, carService);
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        updateCarPage.reader = reader;
        Mockito.when(reader.readLine()).thenReturn("1", "0", "0", "Honda", "0", "Civic", "0", "2021", "0", "25000", "0", "0");
        updateCarPage.run();
        Mockito.verify(carService).update(car);
    }

    @Test
    public void manager_can_update_any_car_details() throws IOException, InterruptedException {
        User manager = new User(2, UserRoles.MANAGER, "manager", "password", "contact", 0);
        CarService carService = Mockito.mock(CarService.class);
        Car car = new Car(2, manager, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        Mockito.when(carService.findById(2)).thenReturn(car);
        UpdateCarPage updateCarPage = new UpdateCarPage(manager, carService);
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        updateCarPage.reader = reader;
        Mockito.when(reader.readLine()).thenReturn("2", "0", "0", "Honda", "0", "Civic", "0", "2021", "0", "25000", "0", "0");
        updateCarPage.run();
        Mockito.verify(carService).update(car);
    }

    @Test
    public void client_can_update_own_car_details() throws IOException, InterruptedException {
        User client = new User(3, UserRoles.CLIENT, "client", "password", "contact", 0);
        CarService carService = Mockito.mock(CarService.class);
        Car car = new Car(3, client, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        Mockito.when(carService.findById(3)).thenReturn(car);
        Mockito.when(carService.findByUser(client.getId())).thenReturn(Collections.singletonList(car));
        UpdateCarPage updateCarPage = new UpdateCarPage(client, carService);
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        updateCarPage.reader = reader;
        Mockito.when(reader.readLine()).thenReturn("3", "0", "0", "Honda", "0", "Civic", "0", "2021", "0", "25000", "0", "0");
        updateCarPage.run();
        Mockito.verify(carService).update(car);
    }
}