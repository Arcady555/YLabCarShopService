package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.pages.client.*;
import ru.parfenov.homework_1.server.pages.manager.AllOrdersPage;
import ru.parfenov.homework_1.server.pages.manager.OrderPage;
import ru.parfenov.homework_1.server.pages.manager.OrderWithMyParametersPage;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManagerPage {
    private final User user;
    private final CarService carService;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ManagerPage(User user, CarService carService, OrderService orderService) {
        this.user = user;
        this.carService = carService;
        this.orderService = orderService;
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            System.out.println("""
                    What operation?
                    0 - view all cars
                    1 - find the car by id
                    2 - find the cars with Your parameters
                    3 - update or delete car
                    4 - view all orders
                    5 - find the order by id
                    6 - find the order with Your parameters
                    7 - update or delete order
                    8 - exit
                    """);
            String answer = reader.readLine();
            switch (answer) {
                case "0" -> {
                    AllCarPage allCarPage = new AllCarPage(carService);
                    allCarPage.run();
                }
                case "1" -> {
                    CarPage carPage = new CarPage(carService);
                    carPage.run();
                }
                case "2" -> {
                    CarWithMyParametersPage carWithMyParametersPage = new CarWithMyParametersPage(carService);
                    carWithMyParametersPage.run();
                }
                case "3" -> {
                    UpdateCarPage updateCarPage = new UpdateCarPage(user, carService);
                    updateCarPage.run();
                }
                case "4" -> {
                    AllOrdersPage allOrdersPage = new AllOrdersPage(orderService);
                    allOrdersPage.run();
                }
                case "5" -> {
                    OrderPage orderPage = new OrderPage(orderService);
                    orderPage.run();
                }
                case "6" -> {
                    OrderWithMyParametersPage orderWithMyParametersPage = new OrderWithMyParametersPage(orderService);
                    orderWithMyParametersPage.run();
                }
                case "7" -> {
                    DeleteOrderPage deleteOrderPage = new DeleteOrderPage(user, orderService);
                    deleteOrderPage.run();
                }
                case "8" -> {
                    return;
                }
                default -> System.out.println("Please enter correct" + System.lineSeparator());
            }
        }
    }
}