package ru.parfenov.homework_3.servlets.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.servlets.DelegatingServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class CreateUserServletTest {
    UserService userService = Mockito.mock(UserService.class);
    CreateUserServlet servlet = new CreateUserServlet(userService);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    PrintWriter writer = new PrintWriter(new StringWriter());
    StringWriter stringWriter = new StringWriter();

    public CreateUserServletTest() throws Exception {
    }

    @Test
    @DisplayName("ADMIN успешно создал юзера")
    public void test_admin_creates_user_successfully() throws Exception {
        User adminUser = new User();
        adminUser.setRole(UserRole.ADMIN);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(adminUser);
        String jsonInput = "{\"role\":\"client\",\"name\":\"John\",\"password\":\"1234\",\"contactInfo\":\"john@example.com\",\"buysAmount\":0}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        Mockito.when(response.getWriter()).thenReturn(writer);

        User createdUser = new User(1, UserRole.CLIENT, "John", "1234", "john@example.com", 0);
        Mockito.when(userService.createByAdmin(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(Optional.of(createdUser));

        servlet.doPost(request, response);

        Mockito.verify(response).setStatus(200);
    }

    @Test
    @DisplayName("Response  содержит корректные данные")
    public void test_response_contains_created_user_details() throws Exception {
        PrintWriter writer = new PrintWriter(stringWriter);
        User adminUser = new User();
        adminUser.setRole(UserRole.ADMIN);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(adminUser);
        String jsonInput = "{\"role\":\"client\",\"name\":\"John\",\"password\":\"1234\",\"contactInfo\":\"john@example.com\",\"buysAmount\":0}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));
        Mockito.when(response.getWriter()).thenReturn(writer);

        User createdUser = new User(1, UserRole.CLIENT, "John", "1234", "john@example.com", 0);
        Mockito.when(userService.createByAdmin(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(Optional.of(createdUser));

        servlet.doPost(request, response);

        String jsonResponse = stringWriter.toString();
        assertTrue(jsonResponse.contains("\"name\":\"John\""));
    }
}