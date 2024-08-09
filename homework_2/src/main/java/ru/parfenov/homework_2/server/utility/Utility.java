package ru.parfenov.homework_2.server.utility;

import ru.parfenov.homework_2.server.model.Car;
import ru.parfenov.homework_2.server.model.Order;
import ru.parfenov.homework_2.server.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Utility {
    private static final Logger LOGGER = Logger.getLogger("LogList");
    public static String logFilePath = "homework_1/src/main/java/ru/parfenov/homework_1/LogList.log";
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