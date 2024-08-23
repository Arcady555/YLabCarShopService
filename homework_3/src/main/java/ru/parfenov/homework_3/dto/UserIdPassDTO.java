package ru.parfenov.homework_3.dto;

import lombok.Data;

/**
 * DTO для удобной подачи в json. Enum заменили на String
 * И оставлены только поля, нужные для ввода при sign-in
 */
@Data
public class UserIdPassDTO {
    private int id;
    private String password;
}
