package ru.parfenov.homework_2.server.pages.client;

import ru.parfenov.homework_2.server.enums.CarCondition;
import ru.parfenov.homework_2.server.model.Car;
import ru.parfenov.homework_2.server.model.User;
import ru.parfenov.homework_2.server.pages.UserMenuPage;
import ru.parfenov.homework_2.server.service.CarService;
import ru.parfenov.homework_2.server.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница, где пользователь может ввести машину в базу данных
 */

public class CreateCarPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CreateCarPage(User user, CarService carService) {
        this.user = user;
        this.carService = carService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter brand");
        String brand = reader.readLine();
        System.out.println("Enter model");
        String model = reader.readLine();
        System.out.println("Enter year of car production");
        int yearOfProd = 0;
        try {
            yearOfProd = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter price");
        int price = 0;
        try {
            price = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter condition 0 - NEW,  another key - USED");
        String answerCondition = reader.readLine();
        CarCondition condition;
        if (answerCondition.equals("0")) {
            condition = CarCondition.NEW;
        } else {
            condition = CarCondition.USED;
        }
        Car car = carService.create(user.getId(), brand, model, yearOfProd, price, condition);
        System.out.println("Congratulation!" +
                System.lineSeparator() +
                "The car is in DataBase." +
                System.lineSeparator() +
                "Remember the ID: " +
                car.getId());
        Utility.logging(user.getId(), "create car");
        Thread.sleep(5000);
    }
}