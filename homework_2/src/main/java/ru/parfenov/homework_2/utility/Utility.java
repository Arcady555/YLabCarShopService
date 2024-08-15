package ru.parfenov.homework_2.utility;

import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utility {
    public static String saveLogPath = "homework_1/src/main/java/ru/parfenov/homework_1/SaveLog.txt";

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

    public static Connection loadConnection(InputStream in) throws ClassNotFoundException, SQLException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("application.properties"));
        Connection connection;
        String url = prop.getProperty("container.JDBC_URL");
        String username = prop.getProperty("container.JDBC_USERNAME");
        String password = prop.getProperty("container.JDBC_PASSWORD");
        String driver = prop.getProperty("container.JDBC_DRIVER");
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}