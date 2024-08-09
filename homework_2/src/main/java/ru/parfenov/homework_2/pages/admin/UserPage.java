package ru.parfenov.homework_2.pages.admin;

import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserPage implements UserMenuPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UserPage(UserService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException {
        System.out.println("Enter user ID");
        int userId;
        try {
            userId = Integer.parseInt(reader.readLine());
            service.findByIdForAdmin(userId);
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
    }
}