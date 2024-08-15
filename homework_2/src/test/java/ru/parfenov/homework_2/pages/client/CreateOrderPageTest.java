package ru.parfenov.homework_2.pages.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class CreateOrderPageTest {
    User user = new User(1, UserRole.CLIENT, "John", "password", "contact", 0);
    OrderService orderService = mock(OrderService.class);
    CarService carService = mock(CarService.class);
    LogService logService = mock(LogService.class);
    CreateOrderPage createOrderPage = new CreateOrderPage(user, orderService, carService, logService);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Создание заказа на не свою машину")
    public void test_create_buy_order_for_non_owned_car() throws IOException, InterruptedException {
        CreateOrderPage createOrderPage = new CreateOrderPage(user, orderService, carService, logService);

        Car car = new Car(2, 2, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        when(carService.findById(2)).thenReturn(car);
        when(orderService.create(1, 2, OrderType.BUY)).thenReturn(new Order(1, 1, 2, OrderType.BUY, OrderStatus.OPEN));

        createOrderPage.reader = reader;
        when(reader.readLine()).thenReturn("2", "0");

        createOrderPage.run();

        verify(orderService).create(1, 2, OrderType.BUY);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(1), contains("create the order with ID:"));
    }

    @Test
    @DisplayName("Создание заказа на свою машину")
    public void test_create_service_order_for_owned_car() throws IOException, InterruptedException {

        Car car = new Car(2, 1, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        when(carService.findById(2)).thenReturn(car);
        when(orderService.create(1, 2, OrderType.SERVICE)).thenReturn(new Order(1, 1, 2, OrderType.SERVICE, OrderStatus.OPEN));

        createOrderPage.reader = reader;
        when(reader.readLine()).thenReturn("2", "1");

        createOrderPage.run();

        verify(orderService).create(1, 2, OrderType.SERVICE);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(1), contains("create the order with ID:"));
    }

    @Test
    @DisplayName("Вызов лога")
    public void test_log_entry_created_on_order_creation() throws IOException, InterruptedException {
        Car car = new Car(2, 2, "Toyota", "Camry", 2020, 20000, CarCondition.NEW);
        when(carService.findById(2)).thenReturn(car);
        when(orderService.create(1, 2, OrderType.BUY)).thenReturn(new Order(1, 1, 2, OrderType.BUY, OrderStatus.OPEN));

        createOrderPage.reader = reader;
        when(reader.readLine()).thenReturn("2", "0");

        createOrderPage.run();

        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(1), contains("create the order with ID:"));
    }
}