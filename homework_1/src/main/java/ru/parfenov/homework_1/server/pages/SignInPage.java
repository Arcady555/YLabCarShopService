package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.CarService;
import ru.parfenov.homework_1.server.service.LogService;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Страница входа в систему.
 * Есть проверки:
 * на корректный ввод ID (должны быть цифры)
 * на наличие такого ID в базе данных
 * на правильный пароль
 * После заполнения юзер переходит(в зависимости от своей роли)
 * или на страницу с функционалом админа
 * или на страницу с функционалом менеджера
 * или на страницу с функционалом клиента
 */

public class SignInPage {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public SignInPage(UserService userService, CarService carService, OrderService orderService, LogService logService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.logService = logService;
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
        Map<UserRoles, UserMenuPage> userMenuMap = Map.of(
                UserRoles.ADMIN, new AdminPage(user, userService, carService, orderService, logService),
                UserRoles.MANAGER, new ManagerPage(user, carService, orderService),
                UserRoles.CLIENT, new ClientPage(user, carService, orderService)
        );
        if (user == null) {
            System.out.println("User not found!");
            run();
        } else {
            System.out.println("Enter password");
            String password = reader.readLine();
            if (password.equals(user.getPassword())) {
                UserMenuPage userMenuPage = userMenuMap.get(user.getRole());
                userMenuPage.run();
            } else {
                System.out.println("Not correct password!\n");
                run();
            }
        }
    }
}