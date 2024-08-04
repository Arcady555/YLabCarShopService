package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignInPage {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public SignInPage(UserService userService, CarService carService, OrderService orderService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
    }

    public void run() throws IOException, InterruptedException {
        System.out.println("Enter Id");
        String idStr = reader.readLine();
        System.out.println(idStr);
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User not found!");
            run();
        } else {
            System.out.println("Enter password");
            String password = reader.readLine();
            if (password.equals(user.getPassword())) {
                if (user.getRole().equals(UserRoles.ADMIN)) {
                    AdminPage page = new AdminPage(user, userService, carService, orderService);
                    page.run();
                } else if (user.getRole().equals(UserRoles.MANAGER)) {
                    ManagerPage page = new ManagerPage(user, carService, orderService);
                    page.run();
                } else {
                    ClientPage page = new ClientPage(user, carService, orderService);
                    page.run();
                }
            } else {
                System.out.println("Not correct password!\n");
                run();
            }
        }
    }
}