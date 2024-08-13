package ru.parfenov.homework_2.pages.client;

import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;
import ru.parfenov.homework_2.service.CarService;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Страница редактирования данных о машине.
 * Если пользователь не админ и не менеджер,
 * то он может редактировать только свои машины.
 */

public class UpdateCarPage implements UserMenuPage {
    private final User user;
    private final CarService carService;
    private final LogService logService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UpdateCarPage(User user, CarService carService, LogService logService) {
        this.user = user;
        this.carService = carService;
        this.logService = logService;
    }

    @Override
    public void run() throws IOException, InterruptedException {
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.MANAGER) {
            System.out.println("This are Your cars:");
            carService.findByOwner(user.getId());
        }
        System.out.println("Enter the id of the desired car");
        int carId = Utility.checkIfReadInt(reader.readLine(), this);
        Car car = carService.findById(carId);
        if (car == null) {
            System.out.println("The car is not founded");
            run();
        } else {
            if (user.getRole() != UserRole.ADMIN &&
                    user.getRole() != UserRole.MANAGER &&
                    car.getOwnerId() != user.getId()) {
                System.out.println("Sorry? It is not Your car! Нou can't perform actions with this car");
                Thread.sleep(5000);
                run();
            }
            System.out.println("Do you want to delete the car?" + System.lineSeparator() + "0 - yes, another key - no");
            if ("0".equals(reader.readLine())) {
                carService.delete(car);
                run();
            } else {
                System.out.println(
                        "Do you want to change brand?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no"
                );
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new brand");
                    car.setBrand(reader.readLine());
                }

                System.out.println(
                        "Do you want to change model?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new model");
                    String newModel = reader.readLine();
                    car.setModel(newModel);
                }

                System.out.println(
                        "Do you want to change year of produce?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new date");
                    int newDate = Utility.checkIfReadInt(reader.readLine(), this);
                    car.setYearOfProd(newDate);
                }

                System.out.println(
                        "Do you want to change price?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new price");
                    String newPriceStr = reader.readLine();
                    int newPrice =  Utility.checkIfReadInt(reader.readLine(), this);
                    car.setPrice(newPrice);
                }

                System.out.println(
                        "Do you want to change condition?" +
                                System.lineSeparator() +
                                "0 - yes, another key - no");
                if ("0".equals(reader.readLine())) {
                    System.out.println("Enter new condition 0 - NEW,  another key - USED");
                    CarCondition condition = "0".equals(reader.readLine()) ? CarCondition.NEW : CarCondition.USED;
                    car.setCondition(condition);
                }

                carService.update(car);
                logService.saveLineInLog(
                        LocalDateTime.now(),
                        user.getId(),
                        "update the car with ID:" + car.getId());
                Thread.sleep(5000);
            }
        }
    }
}