package ru.parfenov.dto;

import lombok.Data;
import ru.parfenov.enums.UserRole;

/**
 * DTO для удобной подачи в json. Enum заменили на String
 * И оставлены только поля, нужные в выводе юзера при sign-in
 */
@Data
public class UserIdNameRoleDTO {
    private int id;
    private UserRole role;
    private String name;
}
