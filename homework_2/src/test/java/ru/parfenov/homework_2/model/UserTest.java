package ru.parfenov.homework_2.model;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.UserRole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    public void create_user_with_all_fields() {
        User user = new User(1, UserRole.ADMIN, "John Doe", "password123", "john.doe@example.com", 5);
        assertEquals(1, user.getId());
        assertEquals(UserRole.ADMIN, user.getRole());
        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals("john.doe@example.com", user.getContactInfo());
        assertEquals(5, user.getBuysAmount());
    }

    @Test
    public void create_user_with_name_and_password() {
        User user = new User("Jane Doe", "password456");
        assertEquals("Jane Doe", user.getName());
        assertEquals("password456", user.getPassword());
        assertEquals(UserRole.CLIENT, user.getRole());
    }

    @Test
    public void get_and_set_user_id() {
        User user = new User();
        user.setId(10);
        assertEquals(10, user.getId());
    }

    @Test
    public void get_and_set_user_role() {
        User user = new User();
        user.setRole(UserRole.MANAGER);
        assertEquals(UserRole.MANAGER, user.getRole());
    }

    @Test
    public void create_user_with_null_optional_fields() {
        User user = new User(2, null, null, null, null, 0);
        assertNull(user.getRole());
        assertNull(user.getName());
        assertNull(user.getPassword());
        assertNull(user.getContactInfo());
        assertEquals(0, user.getBuysAmount());
    }

    @Test
    public void set_role_to_null_and_verify_default() {
        User user = new User();
        user.setRole(null);
        assertNull(user.getRole());
    }

    @Test
    public void set_negative_buys_amount() {
        User user = new User();
        user.setBuysAmount(-5);
        assertEquals(-5, user.getBuysAmount());
    }

    @Test
    public void create_user_with_empty_name() {
        User user = new User("", "password789");
        assertEquals("", user.getName());
        assertEquals("password789", user.getPassword());
        assertEquals(UserRole.CLIENT, user.getRole());
    }
}