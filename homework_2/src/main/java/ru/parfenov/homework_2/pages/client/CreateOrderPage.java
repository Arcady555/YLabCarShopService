package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.pages.manager.OrderWithMyParametersPage;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Страница, где клиент может создать заявку на покупку машины(если машина не его)
 * или на сервис машины(если машина его)
 */

public class CreateOrderPage implements UserMenuPage {
    private final User user;
    private final OrderService orderService;
    private final CarService carService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CreateOrderPage(User user, OrderService orderService, CarService carService, LogService logService) {
        this.user = user;
        this.orderService = orderService;
        this.carService = carService;
        this.logService = logService;
    }

    @Override
    public void run() throws InterruptedException, IOException {
        System.out.println("Enter car id");
        int carId = Utility.checkIfReadInt(reader.readLine(), this);
        if (carService.findById(carId) == null) {
            System.out.println("The car not found!");
            run();
        }

        int ownerId = carService.findById(carId).getOwnerId();
        System.out.println("What the type of order? 0 - BUY,  another key - SERVICE");
        OrderType orderType = OrderType.SERVICE;
        if ("0".equals(reader.readLine())) orderType = OrderType.BUY;

        if (ownerId == user.getId() && orderType == OrderType.BUY) {
            System.out.println("Sorry! But the car is already Your!)))");
            run();
        }

        if (ownerId != user.getId() && orderType == OrderType.SERVICE) {
            System.out.println("Sorry! Only the owner can order the car for service!");
            run();
        }

        Order order = orderService.create(user.getId(), carId, orderType);
        logService.saveLineInLog(
                LocalDateTime.now(),
                user.getId(),
                "create the order with ID:" + order.getId());
        Thread.sleep(5000);
    }
}