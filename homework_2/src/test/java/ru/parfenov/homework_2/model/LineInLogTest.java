package ru.parfenov.homework_2.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.Assert.*;

public class LineInLogTest {

    @Test
    public void create_line_in_log_with_valid_data() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertNotNull(lineInLog);
        assertEquals(id, lineInLog.id());
        assertEquals(time, lineInLog.time());
        assertEquals(userId, lineInLog.userId());
        assertEquals(action, lineInLog.action());
    }

    @Test
    public void retrieve_id_from_line_in_log() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertEquals(id, lineInLog.id());
    }

    @Test
    public void retrieve_time_from_line_in_log() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertEquals(time, lineInLog.time());
    }

    @Test
    public void retrieve_user_id_from_line_in_log() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, userId, action);
        assertEquals(userId, lineInLog.userId());
    }

    @Test
    public void create_line_in_log_with_null_id() {
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(null, time, userId, action);
        assertNotNull(lineInLog);
        assertNull(lineInLog.id());
    }

    @Test
    public void create_line_in_log_with_null_time() {
        Long id = 1L;
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, null, userId, action);
        assertNotNull(lineInLog);
        assertNull(lineInLog.time());
    }

    @Test
    public void create_line_in_log_with_null_user_id() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String action = "login";
        LineInLog lineInLog = new LineInLog(id, time, null, action);
        assertNotNull(lineInLog);
        assertNull(lineInLog.userId());
    }

    @Test
    public void create_line_in_log_with_null_action() {
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        LineInLog lineInLog = new LineInLog(id, time, userId, null);
        assertNotNull(lineInLog);
        assertNull(lineInLog.action());
    }
}