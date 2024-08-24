package ru.parfenov.repository;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.model.LineInLog;
import ru.parfenov.utility.JdbcRequests;
import ru.parfenov.utility.Utility;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс передаёт запросы в хранилище логов о действиях юзеров
 */
@Slf4j
public class LogRepository {
    private final Connection connection;

    public LogRepository() throws Exception {
        this.connection = Utility.loadConnection();
    }

    public LogRepository(Connection connection) throws Exception {
        this.connection = connection;
    }

    /**
     * Создание записи строки лога в БД
     * @param lineInLog LineInLog сущность из блока ru/parfenov/homework_3/model
     */
    public void create(LineInLog lineInLog) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.createLineInLog)) {
            statement.setTimestamp(1, Timestamp.valueOf(lineInLog.time()));
            statement.setString(2, lineInLog.userId());
            statement.setString(3, lineInLog.action());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in LogRepository.create()!", e);
        }
    }

    /**
     * Вывод записей лога, которые ушли в базу данных, по параметрам
     * @param userId ID юзера
     * @param action действие юзера
     * @param dateTimeFrom с какой даты-времени искать логи
     * @param dateTimeTo по какую дату-время искать логи
     * @return список строк-записей логов
     */
    public List<LineInLog> findByParameters(int userId, String action, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        String request = getRequestForFindByParam(userId, action, dateTimeFrom, dateTimeTo);
        List<LineInLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM cs_schema.log_records WHERE " + request)
        ) {
            generateStatementSets(statement, userId, action, dateTimeFrom, dateTimeTo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LineInLog lineInLog = returnLineInLog(resultSet);
                    logs.add(lineInLog);
                }
            }
        } catch (Exception e) {
            log.error("Exception in UserRepositoryJdbcImpl.findByParameters(). ", e);
        }
        return logs;
    }

    private LineInLog returnLineInLog(ResultSet resultSet) throws SQLException {
        return new LineInLog(
                resultSet.getLong("id"),
                resultSet.getTimestamp("date_time").toLocalDateTime().truncatedTo(ChronoUnit.MINUTES),
                resultSet.getString("user_id"),
                resultSet.getString("action")
        );
    }

    private String getRequestForFindByParam(int userId,
                                            String action,
                                            LocalDateTime dateTimeFrom,
                                            LocalDateTime dateTimeTo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userId != 0 ? " user_id = ? and" : "").
                append(!action.isEmpty() ? " action = ? and" : "").
                append(dateTimeFrom != null ? " date_time > ? and" : "").
                append(dateTimeTo != null ? " date_time < ?" : "");
        if (stringBuilder.toString().endsWith("and")) stringBuilder.setLength(stringBuilder.length() - 3);

        return stringBuilder.toString();
    }

    private void generateStatementSets(PreparedStatement statement,
                                       int userId,
                                       String action,
                                       LocalDateTime dateTimeFrom,
                                       LocalDateTime dateTimeTo) throws SQLException {
        int index = 0;
        if (userId != 0) {
            index++;
            statement.setInt(index, userId);
        }
        if (!action.isEmpty()) {
            index++;
            statement.setString(index, action);
        }
        if (dateTimeFrom != null) {
            index++;
            statement.setTimestamp(index, Timestamp.valueOf(dateTimeFrom));
        }
        if (dateTimeTo != null) {
            index++;
            statement.setTimestamp(index, Timestamp.valueOf(dateTimeTo));
        }
    }
}