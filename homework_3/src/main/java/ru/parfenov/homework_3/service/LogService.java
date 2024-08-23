package ru.parfenov.homework_3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.LineInLog;
import ru.parfenov.homework_3.store.LogStore;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
     * @param dateTime Время данного события, которое пошло в лог
     * @param userId ID юзера
     * @param action наименование события
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
     * @param userIdStr ID юзера
     * @param action действие юзера
     * @param dateTimeFomStr с какой даты-времени искать логи
     * @param dateTimeToStr по какую дату-время искать логи
     * @return список строк-записей логов
     */
    public List<LineInLog> findByParameters(String userIdStr, String action, String dateTimeFomStr, String dateTimeToStr) {
        int userId = getIntFromString(userIdStr);
        LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeFomStr);
        LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeToStr);
        return store.findByParameters(userId, action, dateTimeFrom, dateTimeTo);
    }
}