package ru.parfenov.model;

import jakarta.persistence.*;
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

@Entity
@Table(name = "users", schema = "cs_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private PersonRole role;
    private String name;
    private String password;
    @Column(name = "contact_info")
    private String contactInfo;
    @Column(name = "buys_amount")
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