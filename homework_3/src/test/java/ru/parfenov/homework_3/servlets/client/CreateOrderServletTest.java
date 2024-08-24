package ru.parfenov.homework_3.servlets.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.service.OrderService;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.servlets.DelegatingServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CreateOrderServletTest {

    @Test
    @DisplayName("Успешное создание заказа")
    public void test_return_200_and_order_details() throws Exception {
        OrderService orderService = mock(OrderService.class);
        CarService carService = mock(CarService.class);
        UserService userService = mock(UserService.class);
        CreateOrderServlet servlet = new CreateOrderServlet(orderService, carService, userService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setId(1);
        user.setRole(UserRole.CLIENT);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        String jsonInput = "{\"authorId\":1,\"carId\":1,\"type\":\"SERVICE\"}";
        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(jsonInput.getBytes())));

        when(carService.isOwnCar(1, 1)).thenReturn(true);
        Order order = new Order();
        when(orderService.create(1, 1, "SERVICE")).thenReturn(Optional.of(order));

        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
    }
}