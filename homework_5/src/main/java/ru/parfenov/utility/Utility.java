package ru.parfenov.utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utility {
    public static String saveLogPath = "homework_1/src/main/java/ru/parfenov/homework_3/SaveLog.txt";
    public static String nameOfSite = "http://localhost:7070/";

    private Utility() {
    }

    public static int getIntFromString(String str) {
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = 0;
            log.error("Must be number! Not text or empty.", e);
        }
        return result;
    }
}