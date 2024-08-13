package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * Страница позволяет провести поиск по нужным параметрам, в поисковике пропуская enter"ом ненужные параметры
 */
public class CarWithMyParametersPage implements UserMenuPage {
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CarWithMyParametersPage(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        System.out.println("Enter owner id");
        int ownerId = Utility.checkIfReadInt(reader.readLine(), this);

        System.out.println("Enter brand");
        String brand = reader.readLine();

        System.out.println("Enter model");
        String model = reader.readLine();

        System.out.println("Enter year of produce");
        int yearOfProd = Utility.checkIfReadInt(reader.readLine(), this);

        System.out.println("Enter price(from");
        int priceFrom = Utility.checkIfReadInt(reader.readLine(), this);

        System.out.println("Enter price(to)");
        int priceTo = Utility.checkIfReadInt(reader.readLine(), this);

        System.out.println("Enter condition 0 - NEW, enter key - not condition, another key - USED");
        String conditionStr = reader.readLine();
        CarCondition condition = CarCondition.USED;
        if (conditionStr.isEmpty()) condition = null;
        if (conditionStr.equals("0")) condition = CarCondition.NEW;

        carService.findByParameter(ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition);
    }
}