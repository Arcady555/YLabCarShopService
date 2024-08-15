package ru.parfenov.homework_3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.homework_3.enums.UserRole;

/**
 * Модель пользователя приложения
 * У него есть роль с разными правами, которые обнаружатся в дальнейших слоях (клиент, менеджер, админ),
 * имя, пароль, контактная информация, количество покупок автомобилей
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private UserRole role = UserRole.CLIENT;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", " +
                "role: " + getRole() + ", " +
                "name: " + getName() + ", " +
                "contact info: " + getContactInfo() + ", " +
                "buy amount: " + getBuysAmount() + ".";
    }
}