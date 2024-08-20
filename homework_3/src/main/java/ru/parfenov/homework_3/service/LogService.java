package ru.parfenov.homework_3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.CarCondition;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.LineInLog;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.store.LogStore;
import ru.parfenov.homework_3.utility.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
@Slf4j
@AllArgsConstructor
public class LogService implements GettingIntFromString {
    private final LogStore store;

    /**
     * Метод принимает информацию(время события, ID юзера и название его действия) и отправляет
     * её по 2м каналам - в журнал логов и в БД
     */
    public void saveLineInLog(LocalDateTime dateTime, int userId, String action) {
        log.info("date time : {}, user id : {}, action : {}", dateTime, userId, action);
        LineInLog lineInLog = new LineInLog(
                0L,
                dateTime.truncatedTo(ChronoUnit.MINUTES),
                Integer.toString(userId),
                action
        );
        store.create(lineInLog);
    }

    /**
     * Вывод записей лога, которые ушли в базу данных, по параметрам
     */
    public List<LineInLog> findByParameters(String userIdStr, String action, String dateTimeFomStr, String dateTimeToStr) {
        int userId = getIntFromString(userIdStr);
        LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeFomStr);
        LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeToStr);
        return store.findByParameters(userId, action, dateTimeFrom, dateTimeTo);
    }
}