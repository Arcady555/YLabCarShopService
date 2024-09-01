package ru.parfenov.dto;

import lombok.Data;

/**
 * DTO для удобной подачи в json при входе в систему
 * Оставлены только поля, нужные для ввода при sign-in
 */
@Data
public class PersonIdPassDTO {
    private String id;
    private String password;
}
