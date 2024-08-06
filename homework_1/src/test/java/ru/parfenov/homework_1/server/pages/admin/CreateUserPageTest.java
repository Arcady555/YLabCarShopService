package ru.parfenov.homework_1.server.pages.admin;


import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CreateUserPageTest {

    @Test
    public void test_user_role_input_mapping() throws IOException, InterruptedException {
        UserService mockService = mock(UserService.class);
        CreateUserPage page = new CreateUserPage(mockService);
        BufferedReader mockReader = mock(BufferedReader.class);
        page.reader = mockReader;

        when(mockReader.readLine()).thenReturn("0", "testName", "testPassword", "testContactInfo", "5");

        page.run();

        verify(mockService).createByAdmin(anyInt(), eq(UserRoles.ADMIN), anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void test_user_details_read_correctly() throws IOException, InterruptedException {
        UserService mockService = mock(UserService.class);
        CreateUserPage page = new CreateUserPage(mockService);
        BufferedReader mockReader = mock(BufferedReader.class);
        page.reader = mockReader;

        when(mockReader.readLine()).thenReturn("0", "testName", "testPassword", "testContactInfo", "5");

        page.run();

        verify(mockService).createByAdmin(anyInt(), any(UserRoles.class), eq("testName"), eq("testPassword"), eq("testContactInfo"), eq(5));
    }

    @Test
    public void test_create_by_admin_called_with_correct_parameters() throws IOException, InterruptedException {
        UserService mockService = mock(UserService.class);
        CreateUserPage page = new CreateUserPage(mockService);
        BufferedReader mockReader = mock(BufferedReader.class);
        page.reader = mockReader;

        when(mockReader.readLine()).thenReturn("0", "testName", "testPassword", "testContactInfo", "5");

        page.run();

        verify(mockService).createByAdmin(0, UserRoles.ADMIN, "testName", "testPassword", "testContactInfo", 5);
    }

}