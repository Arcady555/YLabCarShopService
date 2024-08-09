package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Страница редактирования данных о машине.
 * Если пользователь не админ и не менеджер,
 * то он может редактировать только свои машины.
 */

public class UpdateCarPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UpdateCarPage(User user, CarService carService) {
        this.user = user;
        this.carService = carService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.MANAGER) {
            System.out.println("This are Your cars:");
            carService.findByOwner(user.getId());
        }
        System.out.println("Enter the id of the desired car");
        int carId = 0;
        try {
            carId = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the NUMBER!");
            run();
        }
        Car car = carService.findById(carId);
        if (car == null) {
            System.out.println("Dou You want out? 0 - yes, another key - no");
            String answer = reader.readLine();
            if (answer.equals("0")) {
                return;
            } else {
                run();
            }
        } else {
            if (user.getRole() != UserRole.ADMIN &&
                    user.getRole() != UserRole.MANAGER &&
                    car.getOwnerId() != user.getId()) {
                System.out.println("Sorry? It is not Your car! Нou can't perform actions with this car");
                run();
            }
            System.out.println("Do you want to delete the car?" + System.lineSeparator() + "0 - yes, another key - no");
            String answerDelete = reader.readLine();
            if (answerDelete.equals("0")) {
                carService.delete(car);
                run();
            } else {
                System.out.println(
                        "Do you want to change brand?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no"
                );
                String answerBrand = reader.readLine();
                if (answerBrand.equals("0")) {
                    System.out.println("Enter new brand");
                    String newBrand = reader.readLine();
                    car.setBrand(newBrand);
                }
                System.out.println(
                        "Do you want to change model?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerModel = reader.readLine();
                if (answerModel.equals("0")) {
                    System.out.println("Enter new model");
                    String newModel = reader.readLine();
                    car.setModel(newModel);
                }
                System.out.println(
                        "Do you want to change year of produce?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerYear = reader.readLine();
                if (answerYear.equals("0")) {
                    System.out.println("Enter new date");
                    int newDate;
                    try {
                        newDate = Integer.parseInt(reader.readLine());
                        car.setYearOfProd(newDate);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter the NUMBER!");
                        run();
                    }
                }
                System.out.println(
                        "Do you want to change price?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerPrice = reader.readLine();
                if (answerPrice.equals("0")) {
                    System.out.println("Enter new price");
                    String newPriceStr = reader.readLine();
                    int newPrice;
                    try {
                        newPrice = Integer.parseInt(newPriceStr);
                        car.setPrice(newPrice);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter the NUMBER!");
                        run();
                    }
                }
                System.out.println(
                        "Do you want to change condition?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                String answerCondition = reader.readLine();
                if (answerCondition.equals("0")) {
                    System.out.println("Enter new condition 0 - NEW,  another key - USED");
                    String answerConditionVar = reader.readLine();
                    if (answerConditionVar.equals("0")) {
                        car.setCondition(CarCondition.NEW);
                    } else {
                        car.setCondition(CarCondition.USED);
                    }
                }
            }
        }
        carService.update(car);
        Utility.logging(user.getId(), "update car info");
        Thread.sleep(5000);
    }
}