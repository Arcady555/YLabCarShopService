package ru.parfenov.homework_2.pages;

import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.admin.*;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.service.UserService;
import ru.parfenov.homework_2.utility.PageGreetings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Страница, на которую попадает юзер, если он админ
 */
public class AdminPage implements UserMenuPage {
    private final User user;
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public AdminPage(User user, UserService userService, CarService carService, OrderService orderService, LogService logService) {
        this.user = user;
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        List<UserMenuPage> adminMenuList = List.of(
                new AllUserPage(userService),
                new UserPage(userService),
                new UserWithMyParametersPage(userService),
                new CreateUserPage(userService, logService),
                new UpdateUserPage(userService),
                new ManagerPage(user, carService, orderService, logService),
                new ClientPage(user, carService, orderService, logService),
                new LogPage(logService)
        );
        while (true) {
            System.out.println(PageGreetings.adminPageMenu);
            String answerStr = reader.readLine();
            UserMenuPage adminMenuPage;
            try {
                int answer = Integer.parseInt(answerStr);
                if (answer == 8) return;
                adminMenuPage = adminMenuList.get(answer);
                if (adminMenuPage == null) {
                    System.out.println("Please enter correct" + System.lineSeparator());
                    run();
                }
                assert adminMenuPage != null;
                adminMenuPage.run();
            } catch (NumberFormatException e) {
                System.out.println("Please enter the NUMBER!" + System.lineSeparator());
                run();
            }
        }
    }
}