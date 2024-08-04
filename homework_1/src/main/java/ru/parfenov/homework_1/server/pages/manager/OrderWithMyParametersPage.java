package ru.parfenov.homework_1.server.pages.manager;

import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderWithMyParametersPage {
    private final OrderService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public OrderWithMyParametersPage(OrderService service) {
        this.service = service;
    }

    public void run() throws IOException {
        System.out.println("Enter order ID");
        int orderId = 0;
        try {
            orderId = Integer.parseInt(reader.readLine());
           // service.findById(orderId);
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter author id");
        int authorId = 0;
        try {
            authorId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter car ID");
        int carId = 0;
        try {
            carId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter type 0 - BUY,  another key - SERVICE");
        String answerType = reader.readLine();
        OrderType type;
        if (answerType.equals("0")) {
            type = OrderType.BUY;
        } else {
            type = OrderType.SERVICE;
        }
        System.out.println("Enter status 0 - OPEN,  another key - CLOSED");
        String answerStatus = reader.readLine();
        OrderStatus status;
        if (answerStatus.equals("0")) {
            status = OrderStatus.OPEN;
        } else {
            status = OrderStatus.CLOSED;
        }
        service.findByParameter(orderId, authorId, carId, type, status);
    }
}