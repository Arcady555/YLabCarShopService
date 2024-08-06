package ru.parfenov.homework_1.server.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LineInLogTest {
    @Test
    public void returns_correct_localdatetime() {
        LocalDateTime expectedTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        LineInLog log = new LineInLog(expectedTime, "user123", "login");
        assertEquals(expectedTime, log.getTime());
    }

    @Test
    public void handles_null_localdatetime() {
        LineInLog log = new LineInLog(null, "user123", "login");
        assertNull(log.getTime());
    }
    @Test
    public void test_returns_correct_userid() {
        LocalDateTime time = LocalDateTime.now();
        String userId = "user123";
        String action = "login";
        LineInLog lineInLog = new LineInLog(time, userId, action);
        assertEquals(userId, lineInLog.getUserId());
    }

    @Test
    public void test_returns_null_when_userid_is_null() {
        LocalDateTime time = LocalDateTime.now();
        String userId = null;
        String action = "login";
        LineInLog lineInLog = new LineInLog(time, userId, action);
        assertNull(lineInLog.getUserId());
    }

}