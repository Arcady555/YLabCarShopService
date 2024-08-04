package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.pages.admin.*;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminPage {
    private final User user;
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public AdminPage(User user, UserService userService, CarService carService, OrderService orderService) {
        this.user = user;
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
    }

    public void run() throws IOException, InterruptedException {
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
                    7 - exit
                    """);
            String answer = reader.readLine();
            switch (answer) {
                case "0" -> {
                    AllUserPage allUserPage = new AllUserPage(userService);
                    allUserPage.run();
                }
                case "1" -> {
                    UserPage userPage = new UserPage(userService);
                    userPage.run();
                }
                case "2" -> {
                    UserWithMyParametersPage userWithMyParametersPage = new UserWithMyParametersPage(userService);
                    userWithMyParametersPage.run();
                }
                case "3" -> {
                    CreateUserPage createUserPage = new CreateUserPage(userService);
                    createUserPage.run();
                }
                case "4" -> {
                    UpdateUserPage updateUserPage = new UpdateUserPage(userService);
                    updateUserPage.run();
                }
                case "5" -> {
                    ManagerPage managerPage = new ManagerPage(user, carService, orderService);
                    managerPage.run();
                }
                case "6" -> {
                    ClientPage clientPage = new ClientPage(user, carService, orderService);
                    clientPage.run();
                }
                case "7" -> {
                    return;
                }
                default -> {
                    System.out.println("Please enter correct" + System.lineSeparator());
                    run();
                }
            }
        }
    }
}