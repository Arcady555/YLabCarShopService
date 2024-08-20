package ru.parfenov.homework_3.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignOutServletTest {
    SignOutServlet servlet = new SignOutServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    public SignOutServletTest() throws Exception {
    }

    @Test
    @DisplayName("Проверка на выброс исключения от response.getWriter")
    public void test_io_exception_getting_print_writer() throws Exception {

        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenThrow(new IOException());

        SignOutServlet servlet = new SignOutServlet();

        assertThrows(IOException.class, () -> {
            servlet.doGet(request, response);
        });
    }
}