package ru.parfenov.homework_2.server.pages;

import ru.parfenov.homework_2.server.model.User;
import ru.parfenov.homework_2.server.pages.client.*;
import ru.parfenov.homework_2.server.pages.manager.AllOrdersPage;
import ru.parfenov.homework_2.server.pages.manager.OrderPage;
import ru.parfenov.homework_2.server.pages.manager.OrderWithMyParametersPage;
import ru.parfenov.homework_2.server.pages.manager.UpdateOrderPage;
import ru.parfenov.homework_2.server.service.CarService;
import ru.parfenov.homework_2.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ManagerPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ManagerPage(User user, CarService carService, OrderService orderService) {
        this.user = user;
        this.carService = carService;
        this.orderService = orderService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        List<UserMenuPage> managerMenuList = List.of(
                new AllCarPage(carService),
                new CarPage(carService),
                new CarWithMyParametersPage(carService),
                new UpdateCarPage(user, carService),
                new AllOrdersPage(orderService),
                new OrderPage(orderService),
                new OrderWithMyParametersPage(orderService),
                new UpdateOrderPage(user, orderService)
        );
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
            String answerStr = reader.readLine();
            UserMenuPage managerMenuPage;
            try {
                int answer = Integer.parseInt(answerStr);
                if (answer == 8) return;
                managerMenuPage = managerMenuList.get(answer);
                if (managerMenuPage == null) {
                    System.out.println("Please enter correct" + System.lineSeparator());
                    run();
                }
                assert managerMenuPage != null;
                managerMenuPage.run();
            } catch (NumberFormatException e) {
                System.out.println("Please enter the NUMBER!" + System.lineSeparator());
                run();
            }
        }
    }
}