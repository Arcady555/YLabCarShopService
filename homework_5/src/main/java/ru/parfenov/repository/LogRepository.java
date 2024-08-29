package ru.parfenov.repository;

import ru.parfenov.model.LineInLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Служит для передачи запросов в хранилище логов о действиях юзеров
 */
public interface LogRepository {

    /**
     * Создание записи строки лога в БД
     *
     * @param lineInLog LineInLog сущность из блока ru/parfenov/homework_3/model
     */
    public void create(LineInLog lineInLog);

    /**
     * Вывод записей лога, которые ушли в базу данных, по параметрам
     *
     * @param userId       ID юзера
     * @param action       действие юзера
     * @param dateTimeFrom с какой даты-времени искать логи
     * @param dateTimeTo   по какую дату-время искать логи
     * @return список строк-записей логов
     */
    List<LineInLog> findByParameters(int userId, String action, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
