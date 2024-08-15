package ru.parfenov;

import ru.parfenov.homework_3.ServerClass;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("HELLO!!!\nWelcome!");
        ServerClass server = new ServerClass();
        server.run();
    }
}