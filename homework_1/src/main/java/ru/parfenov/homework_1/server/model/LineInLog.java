package ru.parfenov.homework_1.server.model;

import java.time.LocalDateTime;

public record LineInLog(LocalDateTime time, String userId, String action) {
}