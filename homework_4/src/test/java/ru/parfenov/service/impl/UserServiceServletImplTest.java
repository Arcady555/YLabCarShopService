package ru.parfenov.service.impl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.enums.UserRole;
import ru.parfenov.model.User;
import ru.parfenov.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceServletImplTest {
    UserRepository repo = mock(UserRepository.class);
    UserServiceServletImpl service = new UserServiceServletImpl(repo);

    @Test
    @DisplayName("Создание через админа с валидными данными")
    public void create_by_admin_with_valid_inputs() {
        User user = new User(0, UserRole.ADMIN, "John", "password", "contact", 5);
        when(repo.create(any(User.class))).thenReturn(user);
        Optional<User> result = service.createByAdmin(1, "admin", "John", "password", "contact", 5);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("John", result.get().getName());
    }

    @Test
    @DisplayName("Создание через регистрацию с валидными данными")
    public void create_by_registration_with_valid_inputs() {
        User user = new User(0, UserRole.CLIENT, "Jane", "password", "contact", 0);
        when(repo.create(any(User.class))).thenReturn(user);
        Optional<User> result = service.createByReg("Jane", "password", "contact");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Jane", result.get().getName());
    }

    @Test
    @DisplayName("Проверка метода findById")
    public void find_by_id_existing_user() {
        User user = new User(1, UserRole.CLIENT, "John", "password", "contact", 0);
        when(repo.findById(1)).thenReturn(user);
        Optional<User> result = service.findById(1);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1, result.get().getId());
    }

    @Test
    @DisplayName("Создание с невалидной ролью")
    public void create_by_admin_with_invalid_role_string() {
        Optional<User> result = service.createByAdmin(1, "invalid_role", "John", "password", "contact", 5);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Создание с пустыми полями")
    public void create_by_registration_with_empty_fields() {
        Optional<User> result = service.createByReg("", "", "");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Поиск по ID которого нет ")
    public void find_by_id_non_existent_user() {
        when(repo.findById(999)).thenReturn(null);
        Optional<User> result = service.findById(999);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Поиск по ID и паролю не получился")
    public void find_by_id_and_incorrect_password() {
        when(repo.findByIdAndPassword(1, "wrong_password")).thenReturn(null);
        Optional<User> result = service.findByIdAndPassword(1, "wrong_password");
        Assertions.assertFalse(result.isPresent());
    }
}