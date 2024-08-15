package ru.parfenov.homework_2.pages;

import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.service.UserService;
import ru.parfenov.homework_2.utility.PageGreetings;

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
        System.out.println(PageGreetings.startPageGreeting);
        String enter = reader.readLine();
        switch (enter) {
            case "1":
                SignUpPage signUpPage = new SignUpPage(logService, userService);
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