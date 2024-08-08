package ru.parfenov.homework_1.server.pages.admin;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class UpdateUserPageTest {

    @Test
    public void test_successful_delete_user() throws IOException, InterruptedException {
        UserService service = mock(UserService.class);
        User user = new User(1, UserRoles.CLIENT, "name", "password", "contact", 10);
        when(service.findByIdForAdmin(1)).thenReturn(user);
        UpdateUserPage page = new UpdateUserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;
        when(reader.readLine()).thenReturn("1", "0");
        page.run();
        verify(service).delete(user);
    }

    @Test
    public void test_find_and_display_user_details() throws IOException, InterruptedException {
        UserService service = mock(UserService.class);
        User user = new User(1, UserRoles.CLIENT, "name", "password", "contact", 10);
        when(service.findByIdForAdmin(1)).thenReturn(user);
        UpdateUserPage page = new UpdateUserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;
        when(reader.readLine()).thenReturn("1");
        page.run();
        verify(service).findByIdForAdmin(1);
    }
}