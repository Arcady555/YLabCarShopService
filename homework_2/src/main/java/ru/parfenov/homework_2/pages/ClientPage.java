package ru.parfenov.homework_2.pages;

import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.client.*;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.utility.PageGreetings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Страница, на которую попадает юзер - клиент
 */
public class ClientPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    private final OrderService orderService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ClientPage(User user, CarService carService, OrderService orderService, LogService logService) {
        this.user = user;
        this.carService = carService;
        this.orderService = orderService;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        List<UserMenuPage> clientMenuList = List.of(
                new CreateCarPage(user, carService, logService),
                new UpdateCarPage(user, carService, logService),
                new AllCarPage(carService),
                new CarPage(carService),
                new CarWithMyParametersPage(carService),
                new CreateOrderPage(user, orderService, carService, logService),
                new DeleteOrderPage(user, orderService, logService)
        );
        while (true) {
            System.out.println(PageGreetings.clientPageMenu);
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