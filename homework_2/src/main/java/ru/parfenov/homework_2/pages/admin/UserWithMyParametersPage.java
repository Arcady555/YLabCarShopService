package ru.parfenov.homework_2.pages.admin;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.UserService;
import ru.parfenov.homework_2.utility.Utility;

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
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter user role 0 - ADMIN,  1 - MANAGER, 2 - CLIENT");
        String answer = reader.readLine();
        UserRole role;
        switch (answer) {
            case "0" -> role = UserRole.ADMIN;
            case "1" -> role = UserRole.MANAGER;
            case "2" -> role = UserRole.CLIENT;
            default -> role = null;
        }
        System.out.println("Enter user name");
        String name = reader.readLine();
        System.out.println("Enter user contact info");
        String contactInfo = reader.readLine();
        System.out.println("Enter buys amount");
        int buysAmount = Utility.checkIfReadInt(reader.readLine(), this);

        service.findByParameters(role, name, contactInfo, buysAmount);
    }
}