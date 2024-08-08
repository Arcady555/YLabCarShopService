package ru.parfenov.homework_1.server.pages.admin;

import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.pages.UserMenuPage;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница, где админ может сам создать любого юзера и с нужным профилем
 */

public class CreateUserPage implements UserMenuPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CreateUserPage(UserService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter user role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
        String answer = reader.readLine();
        UserRoles role = null;
        switch (answer) {
            case "0" -> role = UserRoles.ADMIN;
            case "1" -> role = UserRoles.MANAGER;
            case "2" -> role = UserRoles.CLIENT;
            default -> {
                System.out.println("Please enter correct" + System.lineSeparator());
                run();
            }
        }
        System.out.println("Create user name");
        String name = reader.readLine();
        System.out.println("Create password");
        String password = reader.readLine();
        System.out.println("Create user contact info");
        String contactInfo = reader.readLine();
        System.out.println("Create buys amount");
        int buysAmount = 0;
        try {
            buysAmount = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        service.createByAdmin(0, role, name, password, contactInfo, buysAmount);
        Thread.sleep(5000);
    }
}