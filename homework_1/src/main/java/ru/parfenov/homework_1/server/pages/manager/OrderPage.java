package ru.parfenov.homework_1.server.pages.manager;

import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPage {
    private final OrderService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public OrderPage(OrderService service) {
        this.service = service;
    }

    public void run() throws IOException {
        System.out.println("Enter order ID");
        int orderId;
        try {
            orderId = Integer.parseInt(reader.readLine());
            service.findById(orderId);
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
    }
}
