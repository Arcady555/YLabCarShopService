package ru.homework_5.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class Utility {
    public static String saveLogPath = "homework_5/src/main/resources/logs/SaveLog.txt";
    public static String nameOfSite = "http://localhost:8080/";

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

    public static int getPersonId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String personIdStr = authentication.getName();
        return getIntFromString(personIdStr);
    }
}