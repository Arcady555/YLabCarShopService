package ru.parfenov.homework_2.pages.admin;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserWithMyParametersPage implements UserMenuPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UserWithMyParametersPage(UserService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException {
        System.out.println("Enter user ID");
        int userId = 0;
        try {
            userId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter user role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
        String answer = reader.readLine();
        UserRole role;
        switch (answer) {
            case "0" -> role = UserRole.ADMIN;
            case "1" -> role = UserRole.MANAGER;
            case "2" -> role = UserRole.CLIENT;
            default -> {
                role = null;
                System.out.println("Please enter correct" + System.lineSeparator());
                run();
            }
        }
        System.out.println("Enter user name");
        String name = reader.readLine();
        System.out.println("Enter user contact info");
        String contactInfo = reader.readLine();
        System.out.println("Enter buys amount");
        int buysAmount = 0;
        try {
            buysAmount = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        service.findByParameters(userId, role, name, contactInfo, buysAmount);
    }
}