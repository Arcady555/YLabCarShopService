package ru.parfenov.homework_2.server.pages.admin;

import ru.parfenov.homework_2.server.pages.UserMenuPage;
import ru.parfenov.homework_2.server.service.UserService;

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