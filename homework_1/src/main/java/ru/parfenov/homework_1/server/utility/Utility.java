package ru.parfenov.homework_1.server.utility;

import ru.parfenov.homework_1.server.model.Car;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;

public class Utility {
    public static String adminPassword = "123";

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
}