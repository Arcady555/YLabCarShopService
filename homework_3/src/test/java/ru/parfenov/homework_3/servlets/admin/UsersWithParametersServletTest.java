package ru.parfenov.homework_3.servlets.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.mockito.Mockito.*;

public class UsersWithParametersServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    UserService userService = mock(UserService.class);
    UsersWithParametersServlet servlet = new UsersWithParametersServlet(userService);

    public UsersWithParametersServletTest() throws Exception {
    }

    @Test
    @DisplayName("Если выдал пустой список")
    public void status_code_404_when_no_users_found() throws Exception {
        User adminUser = new User();
        adminUser.setRole(UserRole.ADMIN);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser);
        when(request.getParameter("role")).thenReturn("client");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("contactInfo")).thenReturn("john@example.com");
        when(request.getParameter("buysAmount")).thenReturn("5");

        List<User> users = List.of();
        when(userService.findByParameters("client", "John", "john@example.com", "5")).thenReturn(users);

        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    @DisplayName("Если заданы пустые параметры")
    public void parameters_provided_with_empty_values() {
        User adminUser = new User();
        adminUser.setRole(UserRole.ADMIN);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser);

        when(request.getParameter("role")).thenReturn("");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("contactInfo")).thenReturn("");
        when(request.getParameter("buysAmount")).thenReturn("");

        List<User> users = List.of();

        when(userService.findByParameters("", "", "", "")).thenReturn(users);
    }
}