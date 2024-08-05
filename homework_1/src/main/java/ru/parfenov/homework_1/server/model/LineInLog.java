package ru.parfenov.homework_1.server.model;

import java.time.LocalDateTime;

public class LineInLog {
    private final LocalDateTime time;
    private final String userId;
    private final String action;

    public LineInLog(LocalDateTime time, String userId, String action) {
        this.time = time;
        this.userId = userId;
        this.action = action;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }
}