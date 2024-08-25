package ru.parfenov.controller;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.servlet.http.HttpServletRequest;

/**
 * Интерфейс добавляет в свои имплементации дефолтный метод -
 * вытащить ID юзера, который делает запрос
 */
public interface UserIdInController {
    default int getUserId(HttpServletRequest req) {
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
