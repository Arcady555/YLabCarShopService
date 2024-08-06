package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CreateOrderPageTest {
    @Test
    public void test_create_buy_order_for_non_owned_car() throws IOException, InterruptedException {
        User user = new User(1, UserRoles.CLIENT, "John", "password", "contact", 0);
        OrderService orderService = mock(OrderService.class);
        CarService carService = mock(CarService.class);
        Car car = new Car(2, new User(2, UserRoles.CLIENT, "Jane", "password", "contact", 0), "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        when(carService.findById(2)).thenReturn(car);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("2", "0");
        CreateOrderPage createOrderPage = new CreateOrderPage(user, orderService, carService);
        createOrderPage.reader = reader;
        createOrderPage.run();
        verify(orderService).create(user, 2, OrderType.BUY);
    }

    @Test
    public void test_create_service_order_for_owned_car() throws IOException, InterruptedException {
        User user = new User(1, UserRoles.CLIENT, "John", "password", "contact", 0);
        OrderService orderService = mock(OrderService.class);
        CarService carService = mock(CarService.class);
        Car car = new Car(2, user, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        when(carService.findById(2)).thenReturn(car);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("2", "1");
        CreateOrderPage createOrderPage = new CreateOrderPage(user, orderService, carService);
        createOrderPage.reader = reader;
        createOrderPage.run();
        verify(orderService).create(user, 2, OrderType.SERVICE);
    }
}