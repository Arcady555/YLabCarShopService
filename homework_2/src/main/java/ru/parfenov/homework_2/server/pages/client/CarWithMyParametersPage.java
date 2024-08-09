package ru.parfenov.homework_2.server.pages.client;

import ru.parfenov.homework_2.server.enums.CarCondition;
import ru.parfenov.homework_2.server.pages.UserMenuPage;
import ru.parfenov.homework_2.server.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarWithMyParametersPage implements UserMenuPage {
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CarWithMyParametersPage(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void run() throws IOException {
        System.out.println("Enter car ID");
        int carId = 0;
        try {
            carId = Integer.parseInt(reader.readLine());
            carService.findById(carId);
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter owner id");
        int ownerId = Integer.parseInt(reader.readLine());
        System.out.println("Enter brand");
        String brand = reader.readLine();
        System.out.println("Enter model");
        String model = reader.readLine();
        System.out.println("Enter year of produce");
        int yearOfProd = 0;
        try {
            yearOfProd = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter price(from");
        int priceFrom = 0;
        try {
            priceFrom = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter price(to)");
        int priceTo = 0;
        try {
            priceTo = Integer.parseInt(reader.readLine());
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
        carService.findByParameter(carId, ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition);
    }
}