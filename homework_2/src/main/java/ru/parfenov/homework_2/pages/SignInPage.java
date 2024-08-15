package ru.parfenov.homework_2.pages;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.service.UserService;

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

public class SignInPage implements UserMenuPage {
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

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter Id");
        int id = checkIfReadInt(reader.readLine());
        User user = userService.findById(id);
        Map<UserRole, UserMenuPage> userMenuMap = Map.of(
                UserRole.ADMIN, new AdminPage(user, userService, carService, orderService, logService),
                UserRole.MANAGER, new ManagerPage(user, carService, orderService, logService),
                UserRole.CLIENT, new ClientPage(user, carService, orderService, logService)
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