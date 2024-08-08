package ru.parfenov.homework_1.server.model;

import java.time.LocalDateTime;

/**
 * @param time
 * @param userId
 * @param action
 * Модель строки, которая будет отправлена в лог -
 * время, id юзера, и название его операции
 */
public record LineInLog(LocalDateTime time, String userId, String action) {
}