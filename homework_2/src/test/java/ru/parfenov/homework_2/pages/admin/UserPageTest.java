package ru.parfenov.homework_2.pages.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserPageTest {
    UserService service = mock(UserService.class);
    UserPage userPage = new UserPage(service);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Проверка поиска по id")
    public void test_reads_valid_integer_and_calls_findByIdForAdmin() throws IOException, InterruptedException {
        userPage.reader = reader;
        when(reader.readLine()).thenReturn("1");
        userPage.run();
        verify(service).findByIdForAdmin(1);
    }

    @Test
    @DisplayName("Проверка работы ридера")
    public void test_initialization_of_service_and_reader() {
        assertNotNull(userPage.reader);
    }

    @Test
    @DisplayName("Проверка ввода пустых данных")
    public void test_handles_empty_input_gracefully() throws IOException, InterruptedException {
        userPage.reader = reader;
        when(reader.readLine()).thenReturn("");
        int result = userPage.checkIfReadInt("");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Выброс исключения")
    public void test_handles_ioexception_during_input_reading() throws IOException, InterruptedException {
        userPage.reader = reader;
        when(reader.readLine()).thenThrow(new IOException());

        assertThrows(IOException.class, () -> {
            userPage.run();
        });
    }

    @Test
    @DisplayName("Проверка текста на консоли")
    public void test_prints_prompt_message_to_console() throws IOException, InterruptedException {
        userPage.reader = reader;
        when(reader.readLine()).thenReturn("1");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userPage.run();
        assertTrue(outContent.toString().contains("Enter user ID"));
        System.setOut(originalOut);
    }
}