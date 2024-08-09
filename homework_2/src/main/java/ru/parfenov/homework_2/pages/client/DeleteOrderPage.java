package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница, где клиент может удалить заявку на покупку или обслуживание машины
 * (если он автор этого заказа)
 */

public class DeleteOrderPage implements UserMenuPage {
    private final User user;
    private final OrderService orderService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public DeleteOrderPage(User user, OrderService orderService) {
        this.user = user;
        this.orderService = orderService;
    }

    @Override
    public void run() throws IOException {
        System.out.println("This are Your orders:");
        orderService.findByAuthor(user.getId());
        System.out.println("Enter the id of the desired order");
        int orderId = 0;
        try {
            orderId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        Order order = orderService.findById(orderId);
        if (user.getRole() != UserRole.ADMIN &&
                user.getRole() != UserRole.MANAGER &&
                order.getAuthorId() != user.getId()) {
            System.out.println("Sorry? It is not Your order! You can't perform actions with this order!");
            run();
        }
        System.out.println("Do you want to delete the order?" + System.lineSeparator() + "0 - yes, another key - no");
        String answerDelete = reader.readLine();
        if (answerDelete.equals("0")) {
            orderService.delete(order);
            Utility.logging(user.getId(), "delete order");
        }
    }
}