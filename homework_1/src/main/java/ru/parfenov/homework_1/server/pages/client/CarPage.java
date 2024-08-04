package ru.parfenov.homework_1.server.pages.client;

import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarPage {
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CarPage(CarService carService) {
        this.carService = carService;
    }

    public void run() throws IOException {
        System.out.println("Enter car ID");
        int carId;
        try {
            carId = Integer.parseInt(reader.readLine());
            carService.findById(carId);
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
    }
}
