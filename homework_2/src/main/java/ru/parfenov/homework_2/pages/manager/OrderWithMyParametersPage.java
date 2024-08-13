package ru.parfenov.homework_2.pages.manager;

import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.OrderService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderWithMyParametersPage implements UserMenuPage {
    private final OrderService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public OrderWithMyParametersPage(OrderService service) {
        this.service = service;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter author id");
        int authorId = Utility.checkIfReadInt(reader.readLine(), this);

        System.out.println("Enter car ID");
        int carId = Utility.checkIfReadInt(reader.readLine(), this);

        System.out.println("Enter type 0 - BUY, enter key - not type,  another key - SERVICE");
        String answerType = reader.readLine();
        OrderType type = OrderType.SERVICE;
        if (answerType.isEmpty()) type = null;
        if ("0".equals(answerType)) type = OrderType.BUY;


        System.out.println("Enter status 0 - OPEN, enter key - not type,  another key - CLOSED");
        String answerStatus = reader.readLine();
        OrderStatus status = OrderStatus.CLOSED;
        if (answerStatus.isEmpty()) status = null;
        if ("0".equals(answerStatus)) status = OrderStatus.OPEN;

        service.findByParameter(authorId, carId, type, status);
    }
}