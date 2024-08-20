package ru.parfenov.homework_3.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SignUpServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    String jsonInput = "{\"name\":\"John\",\"password\":\"1234\",\"contactInfo\":\"john@example.com\"}";
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);

    public SignUpServletTest() throws Exception {
    }

    @Test
    @DisplayName("Запуск сервлета и наличие полей на выходе")
    public void testServlet() throws Exception {
        when(response.getWriter()).thenReturn(writer);

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));

        new SignUpServlet().doPost(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("John"));
        assertTrue(stringWriter.toString().contains("1234"));
        assertTrue(stringWriter.toString().contains("john@example.com"));
    }

    @Test
    @DisplayName("Запуск сервлета и наличие методов на выходе")
    public void test_json_response_with_user_details() throws Exception {
        UserService userService = mock(UserService.class);

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        when(userService.createByReg("John", "1234", "john@example.com"))
                .thenReturn(Optional.of(new User("John", "1234")));

        when(response.getWriter()).thenReturn(writer);

        new SignUpServlet().doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }
}