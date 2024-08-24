package ru.parfenov.homework_3.utility;

import ru.parfenov.homework_3.service.*;
import ru.parfenov.homework_3.repository.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utility {
  //  private static final Logger log = LoggerFactory.getLogger(Utility.class);
    public static String saveLogPath = "homework_1/src/main/java/ru/parfenov/homework_3/SaveLog.txt";
    public static String nameOfSite = "http://localhost:7070/";

    private Utility() {
    }

    public static UserService loadUserservice() throws Exception {
        UserRepository store = new UserRepositoryJdbcImpl();
        return new UserServiceServletImpl(store);
    }

    public static LogService loadLogService() throws Exception {
        LogRepository store = new LogRepository();
        return new LogService(store);
    }

    public static CarService loadCarService() throws Exception {
        CarRepository store = new CarRepositoryJdbcImpl();
        return new CarServiceServletImpl(store);
    }

    public static OrderService loadOrderService() throws Exception {
        OrderRepository store = new OrderRepositoryJdbcImpl();
        return new OrderServiceServletImpl(store);
    }

    public static Connection loadConnection() throws ClassNotFoundException, SQLException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("db/liquibase.properties"));
        Class.forName(prop.getProperty("driver-class-name"));
        String url = prop.getProperty("url");
        String login = prop.getProperty("username");
        String password = prop.getProperty("password");
        return DriverManager.getConnection(url, login, password);
    }
}