package ru.parfenov.service.impl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import ru.parfenov.model.LineInLog;
import ru.parfenov.repository.impl.LogRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LogServiceServletImplTest {
    LogRepositoryJdbcImpl repo = mock(LogRepositoryJdbcImpl.class);
    LogServiceServletImpl service = new LogServiceServletImpl(repo);
    LocalDateTime dateTime = LocalDateTime.now();

    @Test
    @DisplayName("Проверка метода Create")
    public void test_saveLineInLog_logs_provided_values() {
        int userId = 123;
        String action = "testAction";
        service.saveLineInLog(dateTime, userId, action);
        verify(repo).create(any(LineInLog.class));
    }

    @Test
    @DisplayName("Проверка операций над LocalDateTime")
    public void test_saveLineInLog_creates_LineInLog_and_calls_repo_create() {
        int userId = 123;
        String action = "testAction";

        service.saveLineInLog(dateTime, userId, action);

        ArgumentCaptor<LineInLog> captor = ArgumentCaptor.forClass(LineInLog.class);
        verify(repo).create(captor.capture());
        LineInLog capturedLog = captor.getValue();

        Assertions.assertEquals(dateTime.truncatedTo(ChronoUnit.MINUTES), capturedLog.time());
        Assertions.assertEquals(Integer.toString(userId), capturedLog.userId());
        Assertions.assertEquals(action, capturedLog.action());
    }

    @Test
    @DisplayName("Проверка метода findByParameters")
    public void test_findByParameters_converts_and_parses_parameters() {
        String userIdStr = "123";
        String action = "testAction";
        String dateTimeFomStr = "2023-01-01T00:00";
        String dateTimeToStr = "2023-01-02T00:00";
        service.findByParameters(userIdStr, action, dateTimeFomStr, dateTimeToStr);
        verify(repo).findByParameters(
                eq(123),
                eq(action),
                eq(LocalDateTime.parse(dateTimeFomStr)),
                eq(LocalDateTime.parse(dateTimeToStr))
        );
    }

    @Test
    @DisplayName("Если ввести null")
    public void test_saveLineInLog_handles_null_values() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            service.saveLineInLog(null, 0, null);
        });
    }

    @Test
    @DisplayName("Если пропущены некоторые параметры")
    public void test_findByParameters_handles_empty_or_null_userIdStr_and_action() {
        service.findByParameters(
                "", "", "2023-01-01T00:00", "2023-01-02T00:00"
        );
        verify(repo).findByParameters(
                eq(0),
                eq(""),
                eq(LocalDateTime.parse("2023-01-01T00:00")),
                eq(LocalDateTime.parse("2023-01-02T00:00"))
        );
        service.findByParameters(
                null, null, "2023-01-01T00:00", "2023-01-02T00:00"
        );
        verify(repo).findByParameters(
                eq(0),
                eq(""),
                eq(LocalDateTime.parse("2023-01-01T00:00")),
                eq(LocalDateTime.parse("2023-01-02T00:00"))
        );
    }
}