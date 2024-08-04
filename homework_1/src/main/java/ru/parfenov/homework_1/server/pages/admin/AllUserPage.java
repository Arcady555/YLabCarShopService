package ru.parfenov.homework_1.server.pages.admin;

import ru.parfenov.homework_1.server.service.UserService;

public class AllUserPage {
    private final UserService service;

    public AllUserPage(UserService service) {
        this.service = service;
    }

    public void run() {
        service.findAll();
    }
}