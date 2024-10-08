package ru.parfenov.homework_1.server.pages.client;

import ru.parfenov.homework_1.server.pages.UserMenuPage;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.IOException;

public class AllCarPage implements UserMenuPage {
    private final CarService carService;

    public AllCarPage(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        carService.findAll();
    }
}