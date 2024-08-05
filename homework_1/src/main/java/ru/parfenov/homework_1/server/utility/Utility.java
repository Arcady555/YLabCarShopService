package ru.parfenov.homework_1.server.utility;

import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Utility {
    private static final Logger LOGGER = Logger.getLogger("LogList");
    public static String adminPassword = "123";
    public static String logFilePath = "homework_1/src/main/java/ru/parfenov/homework_1/LogList.log";
    public static String saveLogPath = "homework_1/src/main/java/ru/parfenov/homework_1/SaveLog.txt";

    private Utility() {
    }

    public static void printUser(User user) {
        if (user != null) {
            System.out.println(
                    "id: " + user.getId() + ", " +
                            "role: " + user.getRole() + ", " +
                            "name: " + user.getName() + ", " +
                            "contact info: " + user.getContactInfo() + ", " +
                            "buy amount: " + user.getBuysAmount() + "."
            );
        }
    }

    public static void printCar(Car car) {
        if (car != null) {
            System.out.println(
                    "id: " + car.getId() + ", " +
                            "owner: " + car.getOwner() + ", " +
                            "brand: " + car.getBrand() + ", " +
                            "model: " + car.getModel() + ", " +
                            "year of produce: " + car.getYearOfProd() + ", " +
                            "price: " + car.getPrice() + ", " +
                            "condition: " + car.getCondition() + "."
            );
        }
    }

    public static void printOrder(Order order) {
        if (order != null) {
            System.out.println(
                    "id: " + order.getId() + ", " +
                            "author: " + order.getAuthor() + ", " +
                            "car id: " + order.getCarId() + ", " +
                            "type: " + order.getType() + ", " +
                            "status: " + order.getStatus() + "."
            );
        }
    }

    public static void logging(int userId, String action) {
        try {
            LOGGER.setUseParentHandlers(false);
            FileHandler fh = new FileHandler(logFilePath, true);
            LOGGER.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.info(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) +
                    " user:" +
                    userId +
                    " action:" +
                    action);
        } catch (SecurityException | IOException e) {
            LOGGER.log(Level.SEVERE, "Произошла ошибка при работе с логом.", e);
        }
    }
}