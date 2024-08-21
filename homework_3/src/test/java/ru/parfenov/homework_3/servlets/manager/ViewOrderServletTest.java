package ru.parfenov.homework_3.servlets.manager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.enums.OrderStatus;
import ru.parfenov.homework_3.enums.OrderType;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.OrderService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ViewOrderServletTest {
    OrderService orderService = mock(OrderService.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    PrintWriter writer = new PrintWriter(new StringWriter());
    ViewOrderServlet servlet = new ViewOrderServlet(orderService);

    public ViewOrderServletTest() throws Exception {
    }

    @Test
    @DisplayName("Выполнение запроса с невалидной ролью(client)")
    public void test_non_manager_role_receives_error_message() throws Exception {
        User user = new User(1, UserRole.CLIENT, "client", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(403);
    }

    @Test
    @DisplayName("Выполнение запроса от неавторизованного юзера")
    public void test_user_not_logged_in_receives_error_message() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Выполнение запроса без роли")
    public void test_user_session_with_user_attribute_but_null_role() throws Exception {
        User user = new User(1, null, "manager", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(403);
    }
}