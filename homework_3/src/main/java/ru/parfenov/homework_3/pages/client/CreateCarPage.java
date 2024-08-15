package ru.parfenov.homework_3.pages.client;

import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.pages.UserMenuPage;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.service.LogService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Страница, где пользователь может ввести машину в базу данных
 */

public class CreateCarPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CreateCarPage(User user, CarService carService, LogService logService) {
        this.user = user;
        this.carService = carService;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter brand");
        String brand = reader.readLine();
        System.out.println("Enter model");
        String model = reader.readLine();
        System.out.println("Enter year of car production");
        int yearOfProd = checkIfReadInt(reader.readLine());

        System.out.println("Enter price");
        int price = 0;
        try {
            price = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        System.out.println("Enter condition 0 - NEW,  another key - USED");
        CarCondition condition = "0".equals(reader.readLine()) ? CarCondition.NEW : CarCondition.USED;
        Car car = carService.create(user.getId(), brand, model, yearOfProd, price, condition);
        System.out.println("Congratulation!" +
                System.lineSeparator() +
                "The car is in DataBase." +
                System.lineSeparator() +
                "Remember the ID: " +
                car.getId());
        logService.saveLineInLog(
                LocalDateTime.now(),
                user.getId(),
                "create the car with ID:" + car.getId());
        Thread.sleep(5000);
    }
}