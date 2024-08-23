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
    @DisplayName("Выполнение запроса от неавторизованного юзера")
    public void test_user_not_logged_in_receives_error_message() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Менеджер может смотреть заказ")
    public void manager_can_view_any_order() throws Exception {
        User user = new User(1, UserRole.MANAGER, "manager", "password", "contact", 0);
        Order order = new Order(1, 2, 3, OrderType.SERVICE, OrderStatus.OPEN);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        when(orderService.findById("1")).thenReturn(Optional.of(order));
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(200);
    }

    @Test
    @DisplayName("Админ может смотреть заказ")
    public void admin_can_view_any_order() throws Exception {
        User user = new User(1, UserRole.ADMIN, "admin", "password", "contact", 0);
        Order order = new Order(1, 2, 3, OrderType.BUY, OrderStatus.OPEN);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        when(orderService.findById("1")).thenReturn(Optional.of(order));
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(200);
    }

    @Test
    @DisplayName("Клиент может смотреть заказ")
    public void client_can_view_own_order() throws Exception {
        User user = new User(1, UserRole.CLIENT, "client", "password", "contact", 0);
        Order order = new Order(1, 1, 3, OrderType.BUY, OrderStatus.OPEN);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        when(orderService.findById("1")).thenReturn(Optional.of(order));
        when(orderService.isOwnOrder(1, 1)).thenReturn(true);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(200);
    }

    @Test
    @DisplayName("Заказ найден, генерация json")
    public void order_found_and_returned_as_json() throws Exception {
        User user = new User(1, UserRole.MANAGER, "manager", "password", "contact", 0);
        Order order = new Order(1, 2, 3, OrderType.BUY, OrderStatus.OPEN);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        when(orderService.findById("1")).thenReturn(Optional.of(order));
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    @DisplayName("Юзер не найден - 404")
    public void user_not_logged_in_response_401() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(401);
    }

    @Test
    @DisplayName("Заказ не найден - 404")
    public void order_id_missing_or_invalid_response_404() throws Exception {
        User user = new User(1, UserRole.MANAGER, "manager", "password", "contact", 0);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(404);
    }
}