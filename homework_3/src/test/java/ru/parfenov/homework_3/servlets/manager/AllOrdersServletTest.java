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

public class AllOrdersServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    PrintWriter writer = new PrintWriter(new StringWriter());
    OrderService orderService = mock(OrderService.class);
    AllOrdersServlet servlet = new AllOrdersServlet(orderService);

    public AllOrdersServletTest() throws Exception {
    }

    @Test
    @DisplayName("Юзеру CLIENT отказано в просмотре")
    public void non_admin_user_attempts_to_retrieve_orders() throws Exception {
        User clientUser = new User(1, UserRole.CLIENT, "client", "password", "contact", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(clientUser);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(403);
    }

    @Test
    @DisplayName("Не авторизованный юзер не смог зайти на страницу")
    public void unauthenticated_user_attempts_to_retrieve_orders() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Отказ если сессия не выдаёт юзера")
    public void session_does_not_contain_user_attribute() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }
}