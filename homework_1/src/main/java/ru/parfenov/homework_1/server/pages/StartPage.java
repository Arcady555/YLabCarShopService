package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.LogService;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Выводит текст - меню для пользователя.
 * Войти в приложение можно или через регистрацию, или через ввод своего ID
 */

public class StartPage {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public StartPage(UserService userService, CarService carService, OrderService orderService, LogService logService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.logService = logService;
    }

    public void run() throws IOException, InterruptedException {
        System.out.println("""
                Please enter:
                1 - registration
                or
                2 - enter id
                """);
        String enter = reader.readLine();
        switch (enter) {
            case "1":
                SignUpPage signUpPage = new SignUpPage(userService);
                signUpPage.run();
                break;
            case "2":
                SignInPage signInPage = new SignInPage(userService, carService, orderService, logService);
                signInPage.run();
                break;
            default:
                System.out.println("Please enter correct" + System.lineSeparator());
        }
        run();
    }
}