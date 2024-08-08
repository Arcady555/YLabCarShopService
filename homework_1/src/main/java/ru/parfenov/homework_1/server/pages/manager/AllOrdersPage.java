package ru.parfenov.homework_1.server.pages.manager;

import ru.parfenov.homework_1.server.pages.UserMenuPage;
import ru.parfenov.homework_1.server.service.OrderService;

public class AllOrdersPage implements UserMenuPage {
    private final OrderService service;

    public AllOrdersPage(OrderService service) {
        this.service = service;
    }

    public void run() {
        service.findAll();
    }
}