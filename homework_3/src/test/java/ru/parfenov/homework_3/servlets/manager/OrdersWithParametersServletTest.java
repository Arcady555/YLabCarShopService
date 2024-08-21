package ru.parfenov.homework_3.servlets.manager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.OrderService;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class OrdersWithParametersServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    OrderService orderService = mock(OrderService.class);
    OrdersWithParametersServlet servlet = new OrdersWithParametersServlet(orderService);

    public OrdersWithParametersServletTest() throws Exception {
    }

    @Test
    @DisplayName("Не manager получает отказ в доступе")
    public void non_manager_user_receives_error_message() throws Exception {
        User user = new User();
        user.setRole(UserRole.CLIENT);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(403);
    }

    @Test
    @DisplayName("Юзер без авторизации получает отказ")
    public void user_not_logged_in_receives_error_message() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }
}