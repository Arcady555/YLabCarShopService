package ru.parfenov.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LineInLogTest {

    @Test
    @DisplayName("Создание строки лога с валидным временем")
    public void create_line_in_log_with_valid_data() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        Assertions.assertNotNull(lineInLog);
        assertEquals(id, lineInLog.getId());
        assertEquals(time, lineInLog.getTime());
        assertEquals(userId, lineInLog.getUserId());
        assertEquals(action, lineInLog.getAction());
    }

    @Test
    @DisplayName("Проверка ID")
    public void retrieve_id_from_line_in_log() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertEquals(id, lineInLog.getId());
    }

    @Test
    @DisplayName("Проверка time")
    public void retrieve_time_from_line_in_log() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertEquals(time, lineInLog.getTime());
    }

    @Test
    @DisplayName("Проверка userId")
    public void retrieve_user_id_from_line_in_log() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertEquals(userId, lineInLog.getUserId());
    }

    @Test
    @DisplayName("Создание строки лога с null time")
    public void create_line_in_log_with_null_time() {
        Long id = 1L;
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, null, userId, action);
        assertNotNull(lineInLog);
        assertNull(lineInLog.getTime());
    }

    @Test
    @DisplayName("Создание строки лога с null userId")
    public void create_line_in_log_with_null_user_id() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, null, action);
        assertNotNull(lineInLog);
        assertNull(lineInLog.getUserId());
    }

    @Test
    @DisplayName("Создание строки лога с null action")
    public void create_line_in_log_with_null_action() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        LineInLog lineInLog = new LineInLog(id, time, userId, null);
        assertNotNull(lineInLog);
        assertNull(lineInLog.getAction());
    }
}