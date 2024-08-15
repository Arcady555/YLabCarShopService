package ru.parfenov.homework_3.pages.client;

import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.pages.UserMenuPage;
import ru.parfenov.homework_3.service.LogService;
import ru.parfenov.homework_3.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Страница, где клиент может удалить заявку на покупку или обслуживание машины
 * (если он автор этого заказа)
 */

public class DeleteOrderPage implements UserMenuPage {
    private final User user;
    private final OrderService orderService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public DeleteOrderPage(User user, OrderService orderService, LogService logService) {
        this.user = user;
        this.orderService = orderService;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("This are Your orders:");
        orderService.findByAuthor(user.getId());
        System.out.println("Enter the id of the desired order");
        int orderId = checkIfReadInt(reader.readLine());

        Order order = orderService.findById(orderId);

        if (user.getRole() != UserRole.ADMIN &&
                user.getRole() != UserRole.MANAGER &&
                order.getAuthorId() != user.getId()) {
            System.out.println("Sorry? It is not Your order! You can't perform actions with this order!");
            run();
        }

        System.out.println("Do you want to delete the order?" + System.lineSeparator() + "0 - yes, another key - no");
        if ("0".equals(reader.readLine())) {
            orderService.delete(order);
            logService.saveLineInLog(
                    LocalDateTime.now(),
                    user.getId(),
                    "delete the order with ID:" + order.getId());
        }
    }
}