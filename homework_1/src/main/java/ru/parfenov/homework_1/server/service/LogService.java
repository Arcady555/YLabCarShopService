package ru.parfenov.homework_1.server.service;

import ru.parfenov.homework_1.server.model.LineInLog;
import ru.parfenov.homework_1.server.store.LogStore;

import java.time.LocalDateTime;
import java.util.List;

public class LogService {
    private final LogStore store;

    public LogService(LogStore store) {
        this.store = store;
    }

    public List<LineInLog> findAll() {
        List<LineInLog> list = store.findAll();
        printLogLines(list);
        return list;
    }

    public List<LineInLog> findByDateTimeTo(LocalDateTime dateTime) {
        List<LineInLog> list = store.findByDateTimeTo(dateTime);
        printLogLines(list);
        return list;
    }

    public List<LineInLog> findByDateTimeFrom(LocalDateTime dateTime) {
        List<LineInLog> list = store.findByDateTimeFrom(dateTime);
        printLogLines(list);
        return list;
    }

    public List<LineInLog> findByUserId(String userId) {
        List<LineInLog> list = store.findByUserId(userId);
        printLogLines(list);
        return list;
    }

    public void saveLog(List<LineInLog> list) {
        store.saveLog(list);
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
}