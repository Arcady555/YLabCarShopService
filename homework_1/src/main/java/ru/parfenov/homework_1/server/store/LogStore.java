package ru.parfenov.homework_1.server.store;

import ru.parfenov.homework_1.server.model.LineInLog;
import ru.parfenov.homework_1.server.utility.Utility;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogStore {
    List<LineInLog> listLog = new ArrayList<>();

    public LogStore() {
        getLogList();
    }

    public List<LineInLog> findAll() {
        return listLog;
    }

    public List<LineInLog> findByDateTimeTo(LocalDateTime dateTime) {
        List<LineInLog> result = new ArrayList<>();
        for (LineInLog element : findAll()) {
            if (element.getTime().isBefore(dateTime)) {
                result.add(element);
            }
        }
        return result;
    }

    public List<LineInLog> findByDateTimeFrom(LocalDateTime dateTime) {
        List<LineInLog> result = new ArrayList<>();
        for (LineInLog element : findAll()) {
            if (element.getTime().isAfter(dateTime)) {
                result.add(element);
            }
        }
        return result;
    }

    public List<LineInLog> findByUserId(String userId) {
        List<LineInLog> result = new ArrayList<>();
        for (LineInLog element : findAll()) {
            String[] array = element.getUserId().split(":");
            if (array[1].equals(userId)) {
                result.add(element);
            }
        }
        return result;
    }

    public void saveLog(List<LineInLog> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utility.saveLogPath))) {
            String data = listToString(list);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLogList() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Utility.logFilePath))) {
            while (reader.readLine() != null) {
                String logStr = reader.readLine();
                String[] array = logStr.split(" ");
                LineInLog lineInLog = new LineInLog(LocalDateTime.parse(array[1]), array[2], array[3]);
                listLog.add(lineInLog);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String listToString(List<LineInLog> list) {
        StringBuilder builder = new StringBuilder();
        for (LineInLog element : list) {
            builder
                    .append("INFO: ")
                    .append(element.getTime())
                    .append(" user: ")
                    .append(element.getUserId())
                    .append(" action: ")
                    .append(element.getAction())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}