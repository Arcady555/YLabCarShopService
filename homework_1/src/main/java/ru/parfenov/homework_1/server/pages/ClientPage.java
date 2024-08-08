package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.pages.client.*;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ClientPage implements UserMenuPage {
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
        List<UserMenuPage> clientMenuList = List.of(
                new CreateCarPage(user, carService),
                new UpdateCarPage(user, carService),
                new AllCarPage(carService),
                new CarPage(carService),
                new CarWithMyParametersPage(carService),
                new CreateOrderPage(user, orderService, carService),
                new DeleteOrderPage(user, orderService)
        );
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
            String answerStr = reader.readLine();
            UserMenuPage clientMenuPage;
            try {
                int answer = Integer.parseInt(answerStr);
                if (answer == 7) return;
                clientMenuPage = clientMenuList.get(answer);
                if (clientMenuPage == null) {
                    System.out.println("Please enter correct" + System.lineSeparator());
                    run();
                }
                assert clientMenuPage != null;
                clientMenuPage.run();
            } catch (NumberFormatException e) {
                System.out.println("Please enter the NUMBER!" + System.lineSeparator());
                run();
            }
        }
    }
}