package ru.parfenov.homework_1.server.pages.admin;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class UserWithMyParametersPageTest {

    @Test
    public void test_valid_user_input() throws IOException {
        UserService service = mock(UserService.class);
        UserWithMyParametersPage page = new UserWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "0", "John Doe", "john.doe@example.com", "5");

        page.run();

        verify(service).findByParameters(1, UserRoles.ADMIN, "John Doe", "john.doe@example.com", 5);
    }

    @Test
    public void test_valid_user_id_and_role() throws IOException {
        UserService service = mock(UserService.class);
        UserWithMyParametersPage page = new UserWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("2", "1", "Jane Doe", "jane.doe@example.com", "10");

        page.run();

        verify(service).findByParameters(2, UserRoles.MANAGER, "Jane Doe", "jane.doe@example.com", 10);
    }

    @Test
    public void test_service_processes_without_errors() throws IOException {
        UserService service = mock(UserService.class);
        UserWithMyParametersPage page = new UserWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("3", "2", "Alice", "alice@example.com", "15");

        page.run();

        verify(service).findByParameters(3, UserRoles.CLIENT, "Alice", "alice@example.com", 15);
    }

    @Test
    public void test_empty_strings_for_name_or_contact_info() throws IOException {
        UserService service = mock(UserService.class);
        UserWithMyParametersPage page = new UserWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("6", "1", "", "", "30");

        page.run();

        verify(service).findByParameters(6, UserRoles.MANAGER, "", "", 30);
    }

    @Test
    public void test_excessively_long_strings_for_name_or_contact_info() throws IOException {
        UserService service = mock(UserService.class);
        UserWithMyParametersPage page = new UserWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        String longString = new String(new char[1000]).replace('\0', 'a');

        when(reader.readLine()).thenReturn("7", "2", longString, longString, "35");

        page.run();

        verify(service).findByParameters(7, UserRoles.CLIENT, longString, longString, 35);
    }
}