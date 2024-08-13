package ru.parfenov.homework_2.pages.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateUserPageTest {
    UserService service = mock(UserService.class);
    User user = new User(1, UserRole.CLIENT, "John", "password", "contact", 5);
    UpdateUserPage page = new UpdateUserPage(service);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Удаление юзера")
    public void test_user_found_deleted_successfully() throws IOException, InterruptedException {
        when(service.findByIdForAdmin(1)).thenReturn(user);
        page.reader = reader;
        when(reader.readLine()).thenReturn("1", "0");

        page.run();

        verify(service).delete(user);
    }

    @Test
    @DisplayName("Обновление юзера")
    public void test_user_found_role_updated() throws IOException, InterruptedException {
        when(service.findByIdForAdmin(1)).thenReturn(user);
        page.reader = reader;
        when(reader.readLine()).thenReturn("1", "1", "0", "0");
        page.run();
        verify(service).update(user);
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("Изменение name")
    public void test_user_found_name_updated() throws IOException, InterruptedException {
        when(service.findByIdForAdmin(1)).thenReturn(user);
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "1", "1", "0", "newName");

        page.run();

        verify(service).update(user);
        assertEquals("newName", user.getName());
    }
}