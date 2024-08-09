package ru.parfenov.homework_2.pages;

import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.UserService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница регистрации.
 * Есть проверки:
 * на корректный ввод ID (должны быть цифры)
 * на не пустой пароль
 * После заполнения юзер вылетает обратно на стартовую страницу
 */

public class SignUpPage {
    private final UserService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public SignUpPage(UserService service) {
        this.service = service;
    }

    public void run() throws IOException, InterruptedException {

        System.out.println("Create name");
        String name = reader.readLine();
        if (name.isEmpty()) {
            name = "no name";
        }
        System.out.println("Create password");
        String password = reader.readLine();
        if (password.isEmpty()) {
            System.out.println("No password!");
            run();
        }
        System.out.println("Enter Your contact info");
        String contactInfo = reader.readLine();
        if (contactInfo.isEmpty()) {
            contactInfo = "no data";
        }
        User user = service.createByReg(name, password, contactInfo);
        Utility.logging(user.getId(), "registration");
        Thread.sleep(5000);
    }
}