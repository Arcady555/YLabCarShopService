package ru.parfenov.utility;

import ru.parfenov.repository.*;
import ru.parfenov.service.*;

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
        UserRepository repo = new UserRepositoryJdbcImpl();
        return new UserServiceServletImpl(repo);
    }

    public static LogService loadLogService() throws Exception {
        LogRepository repo = new LogRepository();
        return new LogService(repo);
    }

    public static CarService loadCarService() throws Exception {
        CarRepository repo = new CarRepositoryJdbcImpl();
        return new CarServiceServletImpl(repo);
    }

    public static OrderService loadOrderService() throws Exception {
        OrderRepository repo = new OrderRepositoryJdbcImpl();
        return new OrderServiceServletImpl(repo);
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