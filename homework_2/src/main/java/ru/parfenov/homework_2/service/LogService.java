package ru.parfenov.homework_2.service;

import ru.parfenov.homework_2.store.LogStore;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и фронтом-страницами,
 * добавляя и изменяя некоторую логику-функционал
 */

public class LogService {
    private final LogStore store;

    public LogService(LogStore store) {
        this.store = store;
    }
/*
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
    } */
}