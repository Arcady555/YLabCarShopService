package ru.parfenov.homework_1.server.pages;

import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        service.createByReg(name, password, contactInfo);
        Thread.sleep(5000);
    }
}