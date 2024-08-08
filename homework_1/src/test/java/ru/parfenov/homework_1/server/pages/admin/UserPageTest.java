package ru.parfenov.homework_1.server.pages.admin;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class UserPageTest {
    @Test
    public void test_user_id_entered_correctly_and_user_found() throws IOException {
        UserService service = mock(UserService.class);
        UserPage userPage = new UserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1");
        userPage.reader = reader;
        userPage.run();
        verify(service).findByIdForAdmin(1);
    }

    @Test
    public void test_user_id_entered_correctly_but_user_not_found() throws IOException {
        UserService service = mock(UserService.class);
        UserPage userPage = new UserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("999");
        userPage.reader = reader;
        userPage.run();
        verify(service).findByIdForAdmin(999);
    }

    @Test
    public void test_user_id_entered_correctly_and_user_has_admin_privileges() throws IOException {
        UserService service = mock(UserService.class);
        UserPage userPage = new UserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("2");
        userPage.reader = reader;
        userPage.run();
        verify(service).findByIdForAdmin(2);
    }

    @Test
    public void test_user_id_input_is_negative_number() throws IOException {
        UserService service = mock(UserService.class);
        UserPage userPage = new UserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("-1");
        userPage.reader = reader;
        userPage.run();
        verify(service).findByIdForAdmin(-1);
    }

    @Test
    public void test_user_id_input_is_zero() throws IOException {
        UserService service = mock(UserService.class);
        UserPage userPage = new UserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("0");
        userPage.reader = reader;
        userPage.run();
        verify(service).findByIdForAdmin(0);
    }

    @Test
    public void test_user_id_input_is_very_large_number() throws IOException {
        UserService service = mock(UserService.class);
        UserPage userPage = new UserPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("999999999");
        userPage.reader = reader;
        userPage.run();
        verify(service).findByIdForAdmin(999999999);
    }
}