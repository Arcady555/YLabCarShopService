package ru.parfenov.homework_1.server.pages.admin;

import ru.parfenov.homework_1.server.model.LineInLog;
import ru.parfenov.homework_1.server.pages.UserMenuPage;
import ru.parfenov.homework_1.server.service.LogService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Страница, где админ может найти в логе инф по клиенту, по дате и по виду операции
 */

public class LogPage implements UserMenuPage {
    private final LogService service;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public LogPage(LogService service) {
        this.service = service;
    }

    public void run() throws IOException {
        System.out.println(
                "Do you want to see all logs?" +
                        System.lineSeparator() +
                        "0 - yes, another key - no"
        );
        String answerBrand = reader.readLine();
        if (answerBrand.equals("0")) {
            List<LineInLog> list = service.findAll();
            saveList(list);
        }
        System.out.println(
                "Do you want to see all logs before the date & time?" +
                        System.lineSeparator() +
                        "0 - yes, another key - no"
        );
        String answerTimeTo = reader.readLine();
        if (answerTimeTo.equals("0")) {
            System.out.println("Enter date & time (format \"2024-08-05T22:13\")");
            List<LineInLog> list = service.findByDateTimeTo(LocalDateTime.parse(reader.readLine()));
            saveList(list);
        }
        System.out.println(
                "Do you want to see all logs after the date & time?" +
                        System.lineSeparator() +
                        "0 - yes, another key - no"
        );
        String answerTimeFrom = reader.readLine();
        if (answerTimeFrom.equals("0")) {
            System.out.println("Enter date & time (format \"2024-08-05T22:13\")");
            List<LineInLog> list = service.findByDateTimeFrom(LocalDateTime.parse(reader.readLine()));
            saveList(list);
        }
        System.out.println(
                "Do you want to see all logs about the user?" +
                        System.lineSeparator() +
                        "0 - yes, another key - no");
        String answerUser = reader.readLine();
        if (answerUser.equals("0")) {
            System.out.println("Enter user ID");
            List<LineInLog> list = service.findByUserId(reader.readLine());
            saveList(list);
        }
    }

    private void saveList(List<LineInLog> list) throws IOException {
        System.out.println(
                "Do You want to save the list? " +
                        System.lineSeparator() +
                        "0 - yes, another key - no"
        );
        if (reader.readLine().equals("0")) {
            service.saveLog(list);
        }
    }
}