package ru.parfenov.homework_1.server.pages.client;

import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateOrderPage {
    private final User user;
    private final OrderService orderService;
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CreateOrderPage(User user, OrderService orderService, CarService carService) {
        this.user = user;
        this.orderService = orderService;
        this.carService = carService;
    }

    public void run() throws InterruptedException, IOException {
        System.out.println("Enter car id");
        int carId = 0;
        try {
            carId = Integer.parseInt(reader.readLine());
            Car car = carService.findById(carId);
            if (car == null) {
                System.out.println("The car not found!");
                run();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        int ownerId = carService.findById(carId).getOwner().getId();
        System.out.println("What the type of order? 0 - BUY,  another key - SERVICE");
        String answerType = reader.readLine();
        OrderType orderType = null;
        if (answerType.equals("0")) {
            if (ownerId == user.getId()) {
                System.out.println("Sorry! But the car is already Your!)))");
                run();
            } else {
                orderType = OrderType.BUY;
            }
        } else {
            if (ownerId != user.getId()) {
                System.out.println("Sorry! Only the owner can order the car for service!");
                run();
            } else {
                orderType = OrderType.SERVICE;
            }
        }
        orderService.create(user, carId, orderType);
        Thread.sleep(5000);
    }
}