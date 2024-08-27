package ru.parfenov.utility;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.servlet.http.HttpServletRequest;

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

    public static int getUserId(HttpServletRequest req) {
        int result = 0;
        String authHead = req.getHeader("Authorization");
        if (authHead != null) {
            byte[] e = Base64.decode(authHead.substring(4));
            String idNPass = new String(e);
            String idStr = idNPass.substring(0, idNPass.indexOf(":"));
            result = Integer.parseInt(idStr);
        }
        return result;
    }
}