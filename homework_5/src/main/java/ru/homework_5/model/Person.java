package ru.homework_5.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.homework_5.enums.Role;

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
    private Role role;
    private String name;
    private String password;
    @Column(name = "contact_info")
    private String contactInfo;
    @Column(name = "buys_amount")
    private int buysAmount;

    @Override
    public String toString() {
        return "id: " + getId() + ", " +
                "role: " + getRole() + ", " +
                "name: " + getName() + ", " +
                "contact info: " + getContactInfo() + ", " +
                "buy amount: " + getBuysAmount() + ".";
    }
}