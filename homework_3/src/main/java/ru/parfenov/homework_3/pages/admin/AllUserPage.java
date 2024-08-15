package ru.parfenov.homework_3.pages.admin;

import ru.parfenov.homework_3.pages.UserMenuPage;
import ru.parfenov.homework_3.service.UserService;

/**
 * Страница вывода списков всех юзеров
 */
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