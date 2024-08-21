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
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ViewUserServletTest {
    UserService userService = mock(UserService.class);
    ViewUserServlet servlet = new ViewUserServlet(userService);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    PrintWriter writer = new PrintWriter(new StringWriter());

    @Test
    @DisplayName("ADMIN успешно посмотрел страницу юзера")
    public void admin_user_retrieves_user_details_by_id() throws Exception {
        User adminUser = new User(1, UserRole.ADMIN, "admin", "password", "contact", 0);
        User user = new User(2, UserRole.CLIENT, "user", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser);
        when(request.getParameter("id")).thenReturn("2");
        when(userService.findById("2")).thenReturn(Optional.of(user));
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Не ADMIN получил отказ")
    public void non_admin_user_receives_no_rights_message() throws Exception {
        User nonAdminUser = new User(1, UserRole.CLIENT, "user", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(nonAdminUser);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(403);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Не авторизованный юзер получил отказ")
    public void unauthenticated_user_receives_registration_required_message() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Поля юзера в формате JSON")
    public void user_details_returned_in_json_format() throws Exception {
        User adminUser = new User(1, UserRole.ADMIN, "admin", "password", "contact", 0);
        User user = new User(2, UserRole.CLIENT, "user", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser);
        when(request.getParameter("id")).thenReturn("2");
        when(userService.findById("2")).thenReturn(Optional.of(user));
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Сессия не выдала аттрибуты запроса")
    public void session_does_not_contain_user_attribute() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }
}