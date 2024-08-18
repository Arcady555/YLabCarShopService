package ru.parfenov.homework_3.dto;

import lombok.Getter;
import lombok.Setter;
import ru.parfenov.homework_3.enums.UserRole;

@Getter
@Setter
public class UserIdNameRoleDTO {
    private int id;
    private UserRole role;
    private String name;
}
