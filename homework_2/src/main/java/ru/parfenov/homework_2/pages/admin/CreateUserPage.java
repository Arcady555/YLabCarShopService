package ru.parfenov.homework_2.pages.admin;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Страница, где админ может сам создать любого юзера и с нужным профилем
 */

public class CreateUserPage implements UserMenuPage {
    private final UserService service;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CreateUserPage(UserService service, LogService logService) {
        this.service = service;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter user role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
        String answer = reader.readLine();
        UserRole role = null;
        switch (answer) {
            case "0" -> role = UserRole.ADMIN;
            case "1" -> role = UserRole.MANAGER;
            case "2" -> role = UserRole.CLIENT;
            default -> {
                System.out.println("Please enter correct" + System.lineSeparator());
                run();
            }
        }
        System.out.println("Create user name");
        String name = reader.readLine();
        System.out.println("Create password");
        String password = reader.readLine();
        if (password.isEmpty()) {
            System.out.println("The password must to be!");
            Thread.sleep(3000);
            run();
        }
        System.out.println("Create user contact info");
        String contactInfo = reader.readLine();
        System.out.println("Create buys amount");
        int buysAmount = checkIfReadInt(reader.readLine());
        User user = service.createByAdmin(0, role, name, password, contactInfo, buysAmount);
        logService.saveLineInLog(LocalDateTime.now(), user.getId(), "registration by admin");
        Thread.sleep(5000);
    }
}