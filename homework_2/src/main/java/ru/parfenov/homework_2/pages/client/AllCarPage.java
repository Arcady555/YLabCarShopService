package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.CarService;

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