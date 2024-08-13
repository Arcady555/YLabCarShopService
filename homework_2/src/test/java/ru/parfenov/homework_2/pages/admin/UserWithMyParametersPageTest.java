package ru.parfenov.homework_2.pages.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;


public class UserWithMyParametersPageTest {
    UserService service = mock(UserService.class);
    UserWithMyParametersPage page = new UserWithMyParametersPage(service);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_user_role_input_mapping() throws IOException, InterruptedException {;
        page.reader = reader;

        when(reader.readLine()).thenReturn("0", "John Doe", "john@example.com", "5");

        page.run();

        verify(service).findByParameters(UserRole.ADMIN, "John Doe", "john@example.com", 5);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_valid_user_inputs() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "Jane Doe", "jane@example.com", "10");

        page.run();

        verify(service).findByParameters(UserRole.MANAGER, "Jane Doe", "jane@example.com", 10);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_user_name_input() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "Jane Doe", "jane@example.com", "10");

        page.run();

        verify(service).findByParameters(UserRole.MANAGER, "Jane Doe", "jane@example.com", 10);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_user_contact_info_input() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("2", "Alice", "alice@example.com", "15");

        page.run();

        verify(service).findByParameters(UserRole.CLIENT, "Alice", "alice@example.com", 15);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_invalid_role_number() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("3", "Charlie", "charlie@example.com", "25");

        page.run();

        verify(service).findByParameters(null, "Charlie", "charlie@example.com", 25);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_empty_string_for_name() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "", "dave@example.com", "30");

        page.run();

        verify(service).findByParameters(UserRole.MANAGER, "", "dave@example.com", 30);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_buys_amount_input() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("0", "Bob", "bob@example.com", "20");

        page.run();

        verify(service).findByParameters(UserRole.ADMIN, "Bob", "bob@example.com", 20);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_empty_string_for_contact_info() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("2", "Eve", "", "35");
        page.run();
        verify(service).findByParameters(UserRole.CLIENT, "Eve", "", 35);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_invalid_user_role_input() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("invalid", "Charlie", "charlie@example.com", "25");
        page.run();
        verify(service).findByParameters(null, "Charlie", "charlie@example.com", 25);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_empty_user_name_input() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("1", "", "dave@example.com", "30");
        page.run();
        verify(service).findByParameters(UserRole.MANAGER, "", "dave@example.com", 30);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_empty_contact_info_input() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("2", "Eve", "", "35");
        page.run();
        verify(service).findByParameters(UserRole.CLIENT, "Eve", "", 35);
    }
}