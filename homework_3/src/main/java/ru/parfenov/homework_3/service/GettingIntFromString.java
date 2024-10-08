package ru.parfenov.homework_3.service;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Данный интерфейс добавляет дефолтный метод - парсинг числа из строки)
 */
public interface GettingIntFromString {
    default int getIntFromString(String str) {
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = 0;
            Logger log = (Logger) LoggerFactory.getLogger(GettingIntFromString.class.getName());
            log.error("Must be number! Not text or empty.", e);
        }
        return result;
    }
}