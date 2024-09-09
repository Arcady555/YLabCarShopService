package ru.homework_5.dto;

import lombok.Data;

/**
 * DTO для удобной подачи в json.
 * Enum заменили на String
 * Оставлены только поля, нужные для ввода при sign-up
 */
@Data
public class PersonNamePasContDTO {
    private String name;
    private String password;
    private String contactInfo;
}
