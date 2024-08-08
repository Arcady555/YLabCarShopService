package ru.parfenov.homework_1.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.homework_1.server.enums.UserRoles;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private UserRoles role = UserRoles.CLIENT;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}