package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarPage implements UserMenuPage {
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CarPage(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter car ID");
        int carId = checkIfReadInt(reader.readLine());
        carService.findById(carId);
    }
}