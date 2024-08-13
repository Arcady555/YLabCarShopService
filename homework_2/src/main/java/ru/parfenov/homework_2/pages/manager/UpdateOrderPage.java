package ru.parfenov.homework_2.pages.manager;

import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Страница, где менеджер может перевести любой заказ в статус ЗАКРЫТ
 */

public class UpdateOrderPage implements UserMenuPage {
    private final User user;
    private final OrderService service;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UpdateOrderPage(User user, OrderService service, LogService logService) {
        this.user = user;
        this.service = service;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter the id of the desired order");
        int orderId = Utility.checkIfReadInt(reader.readLine(), this);
        Order order = service.findById(orderId);
        if (order == null) {
            System.out.println("The order not found!");
            run();
        } else {
            System.out.println("Do you want to delete the order?" + System.lineSeparator() + "0 - yes, another key - no");
            if ("0".equals(reader.readLine())) {
                service.delete(order);
                logService.saveLineInLog(
                        LocalDateTime.now(),
                        user.getId(),
                        " delete the order with ID:" + order.getId());
            } else {
                System.out.println("Do you want to close the order?" + System.lineSeparator() + "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    order.setStatus(OrderStatus.CLOSED);
                    service.update(order);
                    logService.saveLineInLog(
                            LocalDateTime.now(),
                            user.getId(),
                            " close the order with ID:" + order.getId());
                }
            }
        }
    }
}
