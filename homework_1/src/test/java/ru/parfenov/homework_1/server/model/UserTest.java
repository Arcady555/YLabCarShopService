package ru.parfenov.homework_1.server.model;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.UserRoles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {
    @Test
    public void test_create_user_with_valid_parameters() {
        User user = new User(1, UserRoles.ADMIN, "Alice", "password123", "alice@example.com", 10);

        assertEquals(1, user.getId());
        assertEquals(UserRoles.ADMIN, user.getRole());
        assertEquals("Alice", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals("alice@example.com", user.getContactInfo());
        assertEquals(10, user.getBuysAmount());
    }

    @Test
    public void test_create_user_with_null_optional_fields() {
        User user = new User(2, null, "Bob", "password456", null, 0);

        assertEquals(2, user.getId());
        assertNull(user.getRole());
        assertEquals("Bob", user.getName());
        assertEquals("password456", user.getPassword());
        assertNull(user.getContactInfo());
        assertEquals(0, user.getBuysAmount());
    }
}