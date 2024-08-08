package ru.parfenov.homework_1.server.pages.manager;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.pages.UserMenuPage;
import ru.parfenov.homework_1.server.service.OrderService;
import ru.parfenov.homework_1.server.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница, где менеджер может перевести любой заказ в статус ЗАКРЫТ
 */

public class UpdateOrderPage implements UserMenuPage {
    private final User user;
    private final OrderService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UpdateOrderPage(User user, OrderService service) {
        this.user = user;
        this.service = service;
    }

    @Override
    public void run() throws IOException {
        System.out.println("Enter the id of the desired order");
        int oderId = 0;
        try {
            oderId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        Order order = service.findById(oderId);
        if (order != null) {
            System.out.println("Do you want to delete the order?" + System.lineSeparator() + "0 - yes, another key - no");
            if (reader.readLine().equals("0")) {
                service.delete(order);
                Utility.logging(user.getId(), "delete order");
            } else {
                System.out.println("Do you want to close the order?" + System.lineSeparator() + "0 - yes, another key - no");
                if (reader.readLine().equals("0")) {
                    order.setStatus(OrderStatus.CLOSED);
                    service.update(order);
                    Utility.logging(user.getId(), "close order " + order.getId());
                }
            }
        } else {
            System.out.println("Order not found!");
        }
    }
}
