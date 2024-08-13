package ru.parfenov.homework_2.pages.manager;

import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.OrderService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPage implements UserMenuPage {
    private final OrderService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public OrderPage(OrderService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter order ID");
        int orderId = checkIfReadInt(reader.readLine());
        service.findById(orderId);
    }
}
