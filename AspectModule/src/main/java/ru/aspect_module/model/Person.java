package ru.aspect_module.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aspect_module.enums.Role;

/**
 * Модель пользователя приложения
 * У него есть роль с разными правами, которые обнаружатся в дальнейших слоях (клиент, менеджер, админ),
 * имя, пароль, контактная информация, количество покупок автомобилей
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int id;
    private Role role;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;
}