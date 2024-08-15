package ru.parfenov.homework_3.pages.admin;

import ru.parfenov.homework_3.pages.UserMenuPage;
import ru.parfenov.homework_3.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница вывода юзера по введённому id
 */
public class UserPage implements UserMenuPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UserPage(UserService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter user ID");
        int userId = checkIfReadInt(reader.readLine());
        service.findByIdForAdmin(userId);
    }
}