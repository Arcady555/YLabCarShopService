package ru.parfenov.homework_2.server.model;

import java.time.LocalDateTime;

/**
 * Модель строки, которая будет отправлена в лог -
 * время, id юзера, и название его операции
 */
public record LineInLog(Long id, LocalDateTime time, String userId, String action) {
}