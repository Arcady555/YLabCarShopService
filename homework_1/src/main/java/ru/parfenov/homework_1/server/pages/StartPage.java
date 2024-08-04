package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Выводит текст - меню для пользователя
 */

public class StartPage {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public StartPage(UserService userService, CarService carService, OrderService orderService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
    }

    public void run() throws IOException, InterruptedException {
        System.out.println("""
                Please enter:
                1 - registration
                or
                2 - enter login
                """);
        String enter = reader.readLine();
        switch (enter) {
            case "1":
                SignUpPage signUpPage = new SignUpPage(userService);
                signUpPage.run();
                break;
            case "2":
                SignInPage signInPage = new SignInPage(userService, carService, orderService);
                signInPage.run();
                break;
            default:
                System.out.println("Please enter correct" + System.lineSeparator());
        }
        run();
    }
}