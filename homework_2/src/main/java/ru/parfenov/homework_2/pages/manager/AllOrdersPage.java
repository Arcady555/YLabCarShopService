package ru.parfenov.homework_2.pages.manager;

import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.OrderService;

/**
 * Страница вывода всех заказов
 */
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