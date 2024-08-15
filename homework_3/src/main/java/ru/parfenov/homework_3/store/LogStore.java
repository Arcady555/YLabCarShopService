package ru.parfenov.homework_3.store;

import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.LineInLog;
import ru.parfenov.homework_3.utility.JdbcRequests;
import ru.parfenov.homework_3.utility.Utility;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogStore {
    private final Connection connection;

    public LogStore() throws Exception {
        this.connection = Utility.loadConnection();
    }

    public LogStore(Connection connection) throws Exception {
        this.connection = connection;
    }

    public void create(LineInLog lineInLog) {
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.createLineInLog)) {
            statement.setTimestamp(1, Timestamp.valueOf(lineInLog.time()));
            statement.setString(2, lineInLog.userId());
            statement.setString(3, lineInLog.action());
            statement.execute();
        } catch (Exception e) {
            log.error("Exception in LogStore.create()!", e);
        }
    }

    public List<LineInLog> findAll() {
        List<LineInLog> logList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findAllLinesInLog)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LineInLog lineInLog = returnLogRecord(resultSet);
                    logList.add(lineInLog);
                }
            }
        } catch (Exception e) {
            log.error("Exception in LogStore.findAll()!", e);
        }
        return logList;
    }

    public List<LineInLog> findByDateTimeTo(LocalDateTime dateTime) {
        List<LineInLog> logList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findLinesInLogByDateTimeTo)) {
            statement.setTimestamp(1, Timestamp.valueOf(dateTime));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LineInLog lineInLog = returnLogRecord(resultSet);
                    logList.add(lineInLog);
                }
            }
        } catch (Exception e) {
            log.error("Exception in LogStore.findByDateTimeTo()!", e);
        }
        return logList;
    }

    public List<LineInLog> findByDateTimeFrom(LocalDateTime dateTime) {
        List<LineInLog> logList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findLinesInLogByDateTimeFrom)) {
            statement.setTimestamp(1, Timestamp.valueOf(dateTime));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LineInLog lineInLog = returnLogRecord(resultSet);
                    logList.add(lineInLog);
                }
            }
        } catch (Exception e) {
            log.error("Exception in LogStore.findByDateTimeFrom()!", e);
        }
        return logList;
    }

    public List<LineInLog> findByUserId(String userId) {
        List<LineInLog> logList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(JdbcRequests.findLinesInLogByUserId)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LineInLog lineInLog = returnLogRecord(resultSet);
                    logList.add(lineInLog);
                }
            }
        } catch (Exception e) {
            log.error("Exception in LogStore.findByUserId()!", e);
        }
        return logList;
    }

    private LineInLog returnLogRecord(ResultSet resultSet) throws SQLException {
        return new LineInLog(
                resultSet.getLong("id"),
                resultSet.getTimestamp("date_time").toLocalDateTime().truncatedTo(ChronoUnit.MINUTES),
                resultSet.getString("user_id"),
                resultSet.getString("action")
        );
    }
}