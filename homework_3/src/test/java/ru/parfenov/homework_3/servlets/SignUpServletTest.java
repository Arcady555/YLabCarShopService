package ru.parfenov.homework_3.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class SignUpServletTest {
    UserService userService = mock(UserService.class);
    SignUpServlet signUpServlet = new SignUpServlet(userService);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter writer = mock(PrintWriter.class);

    public SignUpServletTest() throws Exception {
    }

    @Test
    @DisplayName("Статус 200 если успех")
    public void test_status_code_success() throws Exception {
        String jsonInput = "{\"name\":\"John\",\"password\":\"password123\",\"contactInfo\":\"john@example.com\"}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        when(response.getWriter()).thenReturn(writer);
        when(userService.createByReg("John", "password123", "john@example.com")).thenReturn(Optional.of(new User("John", "password123")));

        signUpServlet.doPost(request, response);

        verify(response).setStatus(200);
    }

    @Test
    @DisplayName("Корректный json")
    public void test_return_user_details_json() throws Exception {
        String jsonInput = "{\"name\":\"John\",\"password\":\"password123\",\"contactInfo\":\"john@example.com\"}";
        User createdUser = new User("John", "password123");
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        when(response.getWriter()).thenReturn(writer);
        when(userService.createByReg("John", "password123", "john@example.com")).thenReturn(Optional.of(createdUser));

        signUpServlet.doPost(request, response);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(createdUser);
        verify(writer).print(expectedJson);
    }

    @Test
    @DisplayName("Вывод кода 404")
    public void test_status_code_failure() throws Exception {
        String jsonInput = "{\"name\":\"John\",\"password\":\"password123\",\"contactInfo\":\"john@example.com\"}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        when(response.getWriter()).thenReturn(writer);
        when(userService.createByReg("John", "password123", "john@example.com")).thenReturn(Optional.empty());

        signUpServlet.doPost(request, response);

        verify(response).setStatus(404);
    }

    @Test
    @DisplayName("404 если пропущены поля")
    public void test_missing_required_fields() throws Exception {
        String jsonInput = "{\"name\":\"John\"}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        when(response.getWriter()).thenReturn(writer);

        signUpServlet.doPost(request, response);

        verify(response).setStatus(404);
    }
}