package ru.parfenov.homework_2.pages.admin;

import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.UserService;

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