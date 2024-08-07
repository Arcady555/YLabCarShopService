package ru.parfenov.homework_1.server.pages.admin;

import ru.parfenov.homework_1.server.pages.UserMenuPage;
import ru.parfenov.homework_1.server.service.UserService;

public class AllUserPage implements UserMenuPage {
    private final UserService service;

    public AllUserPage(UserService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.findAll();
    }
}