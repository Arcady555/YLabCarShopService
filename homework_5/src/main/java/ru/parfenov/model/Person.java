package ru.parfenov.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.enums.PersonRole;

/**
 * Модель пользователя приложения
 * У него есть роль с разными правами, которые обнаружатся в дальнейших слоях (клиент, менеджер, админ),
 * имя, пароль, контактная информация, количество покупок автомобилей
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "cs_users")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private PersonRole role;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;

    public Person(String name, String password) {
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