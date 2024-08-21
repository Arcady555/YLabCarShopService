package ru.parfenov.homework_3.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class SignInServletTest {
    UserService userService = mock(UserService.class);
    SignInServlet servlet = new SignInServlet(userService);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter writer = new PrintWriter(new StringWriter());
    String jsonInput = "{\"id\":999,\"password\":\"wrongpassword\"}";

    public SignInServletTest() throws Exception {
    }

    @Test
    @DisplayName("Невозможно зайти, если не находит юзера в базе")
    public void test_sign_in_attempt_with_invalid_credentials() throws Exception {

        when(request.getInputStream()).thenReturn(
                new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        when(userService.findByIdAndPassword(999, "wrongpassword")).thenReturn(Optional.empty());
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(response).setStatus(404);
    }
}