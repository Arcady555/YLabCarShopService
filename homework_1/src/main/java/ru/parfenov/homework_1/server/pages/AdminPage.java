package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.pages.admin.*;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.LogService;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AdminPage {
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

    public void run() throws IOException, InterruptedException {
        List<UserMenuPage> adminMenuList = List.of(
                new AllUserPage(userService),
                new UserPage(userService),
                new UserWithMyParametersPage(userService),
                new CreateUserPage(userService),
                new UpdateUserPage(userService),
                new ManagerPage(user, carService, orderService),
                new ClientPage(user, carService, orderService),
                new LogPage(logService)
        );
        while (true) {
            System.out.println("""
                    What operation?
                    0 - view all users
                    1 - find the user by id
                    2 - find the user with Your parameters
                    3 - create user
                    4 - update or delete user
                    5 - manager menu
                    6 - client menu
                    7 - read log
                    8 - exit
                    """);
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