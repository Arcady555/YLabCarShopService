package ru.parfenov.homework_2.utility;

import ru.parfenov.homework_2.model.Car;
import ru.parfenov.homework_2.model.Order;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.pages.UserMenuPage;

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

    public static int checkIfReadInt(String answer, UserMenuPage menuPage) throws IOException, InterruptedException {
        int result = 0;
        if (!answer.isEmpty()) {
            try {
                result = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Please enter the NUMBER!");
                menuPage.run();
            }
        }
        return result;
    }

    public static Connection loadConnection(InputStream in) throws ClassNotFoundException, SQLException {
        var config = new Properties();
        Connection connection;
        try (in) {
            config.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        String url = loadSysEnvIfNullThenConfig("JDBC_URL", "url", config);
        String username = loadSysEnvIfNullThenConfig("JDBC_USERNAME", "username", config);
        String password = loadSysEnvIfNullThenConfig("JDBC_PASSWORD", "password", config);
        String driver = loadSysEnvIfNullThenConfig("JDBC_DRIVER", "driver-class-name", config);
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    private static String loadSysEnvIfNullThenConfig(String sysEnv, String key, Properties config) {
        String value = System.getenv(sysEnv);
        if (value == null) {
            value = config.getProperty(key);
        }
        return value;
    }
}