package ru.parfenov.homework_3.servlets.client;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeleteOrderServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    @Test
    @DisplayName("Проверка сессии, выдача аттрибута")
    public void test_user_session_attribute_casting() throws Exception {
        User user = new User(1, UserRole.CLIENT, "testUser", "password", "contactInfo", 0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        DeleteOrderServlet servlet = new DeleteOrderServlet();

        assertEquals(user, session.getAttribute("user"));
    }

    @Test
    @DisplayName("Верификация запроса и ответа")
    public void test_request_response_handling() throws Exception {
        OrderService orderService = mock(OrderService.class);
        User user = new User();
        user.setId(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("123");
        when(orderService.delete("123")).thenReturn(true);

        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        DeleteOrderServlet servlet = new DeleteOrderServlet();
        servlet.doDelete(request, response);

        verify(request).getSession();
        verify(response).getWriter();
    }
}