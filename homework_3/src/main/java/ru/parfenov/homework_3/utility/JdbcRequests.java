package ru.parfenov.homework_3.utility;

public class JdbcRequests {
    public static String createUser = "INSERT INTO cs_schema.users(" +
            "user_role," +
            " name," +
            " password," +
            " contact_info," +
            " buys_amount" +
            ")" +
            " VALUES (?, ?, ?, ?, ?)";
    public static String findUserById = "SELECT * FROM cs_schema.users WHERE id = ?";
    public static String findUserByIdAndPassword = "SELECT * FROM cs_schema.users WHERE id = ? AND password = ?";
    public static String deleteUser = "UPDATE cs_schema.users delete WHERE id = ?";
    public static String findAllUsers = "SELECT * FROM cs_schema.users";

    public static String createOrder = "INSERT INTO cs_schema.orders(" +
            "author_id," +
            " car_id," +
            " order_type," +
            " order_status" +
            ")" +
            " VALUES (?, ?, ?, ?)";
    public static String findOrderById = "SELECT * FROM cs_schema.orders WHERE id = ?";
    public static String deleteOrder = "UPDATE cs_schema.orders delete WHERE id = ?";
    public static String updateOrder = "UPDATE cs_schema.orders SET order_status = 'closed' WHERE id = ?";
    public static String findAllOrders = "SELECT * FROM cs_schema.orders";
    public static String findOrderByAuthor = "SELECT * FROM cs_schema.orders WHERE author_id = ?";

    public static String createLineInLog = "INSERT INTO cs_schema.log_records(" +
            "date_time," +
            " user_id," +
            " action" +
            ")" +
            " VALUES (?, ?, ?)";
    public static String findAllLinesInLog = "SELECT * FROM cs_schema.log_records";
    public static String findLinesInLogByDateTimeTo = "SELECT * FROM cs_schema.log_records WHERE date_time < ?";
    public static String findLinesInLogByDateTimeFrom = "SELECT * FROM cs_schema.log_records WHERE date_time > ?";
    public static String findLinesInLogByUserId = "SELECT * FROM cs_schema.log_records WHERE user_id = ?";

    public static String createCar = "INSERT INTO cs_schema.cars(" +
            "owner_id," +
            " brand," +
            " model," +
            " year_of_prod," +
            " price," +
            " car_condition" +
            ")" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    public static String findCarById = "SELECT * FROM cs_schema.cars WHERE id = ?";
    public static String findCarByOwner = "SELECT * FROM cs_schema.cars WHERE owner_id = ?";
    public static String deleteCar = "UPDATE cs_schema.cars delete WHERE id = ?";
    public static String findAllCars = "SELECT * FROM cs_schema.cars";
}
