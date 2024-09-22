package ru.homework_5.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework_5.model.LineInLog;
import ru.homework_5.service.LogService;
import ru.homework_5.repository.LogRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.homework_5.utility.Utility.getIntFromString;
import static ru.homework_5.utility.Utility.saveLogPath;


@Slf4j
@Service
public class LogServiceSpringImpl implements LogService {
    private final LogRepository repo;

    @Autowired
    public LogServiceSpringImpl(LogRepository repo) {
        this.repo = repo;
    }

    /**
     * Метод выводит список по заданным параметрам
     * и сохраняет его в файл.
     * Таким образом, в приложении, помимо файла со всеми записями лога,
     * есть ещё файлы для аудита  --  с частично выбранными записями лога.
     * Данные полученные через этот метод ещё и сохраняются в отдельный текстовый файл.
     *
     *
     * @param userIdStr      ID юзера
     * @param action         действие юзера
     * @param dateTimeFomStr с какой даты-времени искать логи
     * @param dateTimeToStr  по какую дату-время искать логи
     * @return список строк-записей логов
     */

    @Override
    public List<LineInLog> findByParameters(String userIdStr, String action, String dateTimeFomStr, String dateTimeToStr) {
        int userId = getIntFromString(userIdStr);
        LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeFomStr);
        LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeToStr);
        List<LineInLog> result = repo.findByParameters(userId, action, dateTimeFrom, dateTimeTo);
        saveList(result);
        return result;
    }

    private void saveList(List<LineInLog> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveLogPath))) {
            String data = listToString(list);
            writer.write(data);
        } catch (IOException e) {
            log.error("Exception in saveList!", e);
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