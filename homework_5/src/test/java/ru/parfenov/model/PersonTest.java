package ru.parfenov.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.enums.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonTest {

    @Test
    @DisplayName("Создание юзера со всеми полями")
    public void create_user_with_all_fields() {
        Person person = new Person(1, Role.ADMIN, "John Doe", "password123", "john.doe@example.com", 5);
        assertEquals(1, person.getId());
        assertEquals(Role.ADMIN, person.getRole());
        assertEquals("John Doe", person.getName());
        assertEquals("password123", person.getPassword());
        assertEquals("john.doe@example.com", person.getContactInfo());
        assertEquals(5, person.getBuysAmount());
    }

    @Test
    @DisplayName("Проверка сеттера ID")
    public void get_and_set_user_id() {
        Person person = new Person();
        person.setId(10);
        assertEquals(10, person.getId());
    }

    @Test
    @DisplayName("Проверка сеттера ROLE")
    public void get_and_set_user_role() {
        Person person = new Person();
        person.setRole(Role.MANAGER);
        assertEquals(Role.MANAGER, person.getRole());
    }

    @Test
    @DisplayName("Создание юзера с пустыми полями")
    public void create_user_with_null_optional_fields() {
        Person person = new Person(2, null, null, null, null, 0);
        assertNull(person.getRole());
        assertNull(person.getName());
        assertNull(person.getPassword());
        assertNull(person.getContactInfo());
        assertEquals(0, person.getBuysAmount());
    }

    @Test
    @DisplayName("Проверка сеттера на нулл")
    public void set_role_to_null_and_verify_default() {
        Person person = new Person();
        person.setRole(null);
        assertNull(person.getRole());
    }

    @Test
    @DisplayName("Проверка сеттера числа покупок")
    public void set_negative_buys_amount() {
        Person person = new Person();
        person.setBuysAmount(-5);
        assertEquals(-5, person.getBuysAmount());
    }
}