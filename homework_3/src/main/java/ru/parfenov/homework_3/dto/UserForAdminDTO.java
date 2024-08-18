package ru.parfenov.homework_3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForAdminDTO {
    private int id;
    private String role;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;
}
