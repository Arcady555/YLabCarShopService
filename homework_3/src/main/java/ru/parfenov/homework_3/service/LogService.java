package ru.parfenov.homework_3.service;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.LineInLog;
import ru.parfenov.homework_3.store.LogStore;
import ru.parfenov.homework_3.utility.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */

@Slf4j
public class LogService {
    private final LogStore store;

    public LogService(LogStore store) {
        this.store = store;
    }

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
     * Вывод всех записей лога, которые ушли в базу данных
     */
    public List<LineInLog> findAll() {
        List<LineInLog> result = store.findAll();
        printLogLines(result);
        return result;
    }

    /**
     * Фильтрация списка записей лога по времени до заданной даты
     */
    public List<LineInLog> findByDateTimeTo(LocalDateTime dateTime) {
        List<LineInLog> result = store.findByDateTimeTo(dateTime);
        printLogLines(result);
        return result;
    }

    /**
     * Фильтрация списка записей лога по времени после заданной даты
     */
    public List<LineInLog> findByDateTimeFrom(LocalDateTime dateTime) {
        List<LineInLog> result = store.findByDateTimeFrom(dateTime);
        printLogLines(result);
        return result;
    }

    /**
     * Фильтрация списка записей лога по юзеру
     */
    public List<LineInLog> findByUserId(String userId) {
        List<LineInLog> result = store.findByUserId(userId);
        printLogLines(result);
        return result;
    }

    /**
     * Список лога можно сохранить в файл по заданной траектории
     */
    public void saveLogList(List<LineInLog> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utility.saveLogPath))) {
            String data = listToString(list);
            writer.write(data);
        } catch (IOException e) {
            log.error("IOException in LogService.saveLogList(). List isn't saved!", e);
        }
    }

    private void printLogLines(List<LineInLog> list) {
        for (LineInLog element : list) {
            System.out.println(
                    element.time() + " " +
                            element.userId() + " " +
                            element.action()
            );
        }
    }

    private String listToString(List<LineInLog> list) {
        StringBuilder builder = new StringBuilder();
        for (LineInLog element : list) {
            builder
                    .append("INFO: ")
                    .append(element.time())
                    .append(" user: ")
                    .append(element.userId())
                    .append(" action: ")
                    .append(element.action())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}