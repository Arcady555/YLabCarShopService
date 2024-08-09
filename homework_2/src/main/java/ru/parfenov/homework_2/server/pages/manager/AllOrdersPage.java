package ru.parfenov.homework_2.server.pages.manager;

import ru.parfenov.homework_2.server.pages.UserMenuPage;
import ru.parfenov.homework_2.server.service.OrderService;

public class AllOrdersPage implements UserMenuPage {
    private final OrderService service;

    public AllOrdersPage(OrderService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.findAll();
    }
}