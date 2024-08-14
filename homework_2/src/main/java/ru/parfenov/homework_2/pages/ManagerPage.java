package ru.parfenov.homework_2.pages;

import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.client.AllCarPage;
import ru.parfenov.homework_2.pages.client.CarPage;
import ru.parfenov.homework_2.pages.client.CarWithMyParametersPage;
import ru.parfenov.homework_2.pages.client.UpdateCarPage;
import ru.parfenov.homework_2.pages.manager.OrderWithMyParametersPage;
import ru.parfenov.homework_2.pages.manager.AllOrdersPage;
import ru.parfenov.homework_2.pages.manager.OrderPage;
import ru.parfenov.homework_2.pages.manager.UpdateOrderPage;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Страница, на которую попадает юзер, если он менеджер
 */
public class ManagerPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    private final OrderService orderService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ManagerPage(User user, CarService carService, OrderService orderService, LogService logService) {
        this.user = user;
        this.carService = carService;
        this.orderService = orderService;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        List<UserMenuPage> managerMenuList = List.of(
                new AllCarPage(carService),
                new CarPage(carService),
                new CarWithMyParametersPage(carService),
                new UpdateCarPage(user, carService, logService),
                new AllOrdersPage(orderService),
                new OrderPage(orderService),
                new OrderWithMyParametersPage(orderService),
                new UpdateOrderPage(user, orderService, logService)
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