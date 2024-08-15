package ru.parfenov.homework_3.utility;

import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.model.User;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utility {
    public static String saveLogPath = "homework_1/src/main/java/ru/parfenov/homework_3/SaveLog.txt";

    private Utility() {
    }

    public static void printUser(User user) {
        if (user != null) {
            System.out.println(user);
        }
    }

    public static void printCar(Car car) {
        if (car != null) {
            System.out.println(car);
        }
    }

    public static void printOrder(Order order) {
        if (order != null) {
            System.out.println(order);
        }
    }

    public static Connection loadConnection() throws ClassNotFoundException, SQLException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("homework_3/src/main/resources/db/liquibase.properties"));     //(new FileInputStream("application.properties"));
        Class.forName(prop.getProperty("driver-class-name"));
        String url = prop.getProperty("url");
        String login = prop.getProperty("username");
        String password = prop.getProperty("password");
        return DriverManager.getConnection(url, login, password);
    }
}