package ru.parfenov.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.parfenov.model.LineInLog;
import ru.parfenov.repository.LogRepository;
import ru.parfenov.utility.JdbcRequests;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class LogRepositoryJdbcImpl implements LogRepository {
    private final Connection connection;

    @Autowired
    public LogRepositoryJdbcImpl(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public void create(LineInLog lineInLog) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.createLineInLog)) {
            statement.setTimestamp(1, Timestamp.valueOf(lineInLog.time()));
            statement.setString(2, lineInLog.userId());
            statement.setString(3, lineInLog.action());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in LogRepositoryJdbcImpl.create()!", e);
        }
    }

    @Override
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