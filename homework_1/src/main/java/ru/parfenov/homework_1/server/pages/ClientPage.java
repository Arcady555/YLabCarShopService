package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.pages.client.*;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientPage {
    private final User user;
    private final CarService carService;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ClientPage(User user, CarService carService, OrderService orderService) {
        this.user = user;
        this.carService = carService;
        this.orderService = orderService;
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            System.out.println("""
                    What operation?
                    0 - create car
                    1 - update or delete Your cars
                    2 - view all cars
                    3 - find the car by id
                    4 - find the cars with Your parameters
                    5 - create order
                    6 - delete Your order
                    7 - exit
                    """);
            String answer = reader.readLine();
            switch (answer) {
                case "0" -> {
                    CreateCarPage createCarPage = new CreateCarPage(user, carService);
                    createCarPage.run();
                }
                case "1" -> {
                    UpdateCarPage updateCarPage = new UpdateCarPage(user, carService);
                    updateCarPage.run();
                }
                case "2" -> {
                    AllCarPage allCarPage = new AllCarPage(carService);
                    allCarPage.run();
                }
                case "3" -> {
                    CarPage carPage = new CarPage(carService);
                    carPage.run();
                }
                case "4" -> {
                    CarWithMyParametersPage carWithMyParametersPage = new CarWithMyParametersPage(carService);
                    carWithMyParametersPage.run();
                }
                case "5" -> {
                    CreateOrderPage createOrderPage = new CreateOrderPage(user, orderService, carService);
                    createOrderPage.run();
                }
                case "6" -> {
                    DeleteOrderPage deleteOrderPage = new DeleteOrderPage(user, orderService);
                    deleteOrderPage.run();
                }
                case "7" -> {
                    return;
                }
                default -> System.out.println("Please enter correct" + System.lineSeparator());
            }
        }
    }
}