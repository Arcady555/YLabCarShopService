package ru.parfenov.homework_3.utility;

public class PageGreetings {
    public static String startPageGreeting = """
            Please enter:
            1 - registration
            or
            2 - enter id
            """;
    public static String managerPageMenu = """
            What operation?
            0 - view all cars
            1 - find the car by id
            2 - find the cars with Your parameters
            3 - update or delete car
            4 - view all orders
            5 - find the order by id
            6 - find the order with Your parameters
            7 - update or delete order
            8 - exit
            """;
    public static String clientPageMenu = """
            What operation?
            0 - create car
            1 - update or delete Your cars
            2 - view all cars
            3 - find the car by id
            4 - find the cars with Your parameters
            5 - create order
            6 - delete Your order
            7 - exit
            """;
    public static String adminPageMenu = """
            What operation?
            0 - view all users
            1 - find the user by id
            2 - find the user with Your parameters
            3 - create user
            4 - update or delete user
            5 - manager menu
            6 - client menu
            7 - read log
            8 - exit
            """;

}
