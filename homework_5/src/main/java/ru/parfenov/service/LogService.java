package ru.parfenov.service;

import ru.parfenov.model.LineInLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс данного слоя служит прослойкой между голым хранилищем с его строгими методами и сервлетами,
 * добавляя и изменяя некоторую логику-функционал
 */
public interface LogService {
    /**
     * Метод принимает информацию(время события, ID юзера и название его действия)
     * и отправляет её в БД
     *
     * @param dateTime Время данного события, которое пошло в лог
     * @param userId   ID юзера
     * @param action   наименование события
     */
    void saveLineInLog(LocalDateTime dateTime, int userId, String action);

    /**
     * Вывод записей лога, которые ушли в базу данных, по параметрам
     *
     * @param userIdStr      ID юзера
     * @param action         действие юзера
     * @param dateTimeFomStr с какой даты-времени искать логи
     * @param dateTimeToStr  по какую дату-время искать логи
     * @return список строк-записей логов
     */
    List<LineInLog> findByParameters(
            String userIdStr,
            String action,
            String dateTimeFomStr,
            String dateTimeToStr
    );
}
