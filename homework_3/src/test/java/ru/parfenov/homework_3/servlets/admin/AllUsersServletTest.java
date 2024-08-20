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

public class AllUsersServletTest {
    UserService userService = mock(UserService.class);
    HttpSession session = mock(HttpSession.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter writer = new PrintWriter(new StringWriter());
    AllUsersServlet servlet = new AllUsersServlet();

    public AllUsersServletTest() throws Exception {
    }

    @Test
    @DisplayName("ADMIN успешно получил лист и json")
    public void admin_user_requests_all_users() throws Exception {
        List<User> users = List.of(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(userService.findAll()).thenReturn(users);
        when(session.getAttribute("user")).thenReturn(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Статус 200 в случае успеха")
    public void response_status_200_when_users_found() throws Exception {
        List<User> users = List.of(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(userService.findAll()).thenReturn(users);
        when(session.getAttribute("user")).thenReturn(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setStatus(200);
    }

    @Test
    @DisplayName("В ответе формат json")
    public void response_content_type_application_json() throws Exception {
        List<User> users = List.of(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(userService.findAll()).thenReturn(users);
        when(session.getAttribute("user")).thenReturn(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(request.getSession()).thenReturn(session);
        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setContentType("application/json");
    }

    @Test
    @DisplayName("В ответе UTF-8")
    public void response_character_encoding_utf8() throws Exception {
        List<User> users = List.of(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(userService.findAll()).thenReturn(users);
        when(session.getAttribute("user")).thenReturn(new User(1, UserRole.ADMIN, "admin", "password", "contact", 0));
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("CLIENT не смог получить данные")
    public void non_admin_user_receives_403_status() throws Exception {
        when(session.getAttribute("user")).thenReturn(new User(2, UserRole.CLIENT, "client", "password", "contact", 0));
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setStatus(403);
    }

    @Test
    @DisplayName("Не авторизованный не смог получит данные")
    public void unauthenticated_user_receives_401_status() throws Exception {
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Если сессия не дала параметр юзера")
    public void session_does_not_contain_user_attribute() throws Exception {
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }
}