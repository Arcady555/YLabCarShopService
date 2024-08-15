package ru.parfenov.homework_3.pages.client;

import ru.parfenov.homework_3.pages.UserMenuPage;
import ru.parfenov.homework_3.service.CarService;

import java.io.IOException;

/**
 * Страница вывода списка всех машин
 */
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