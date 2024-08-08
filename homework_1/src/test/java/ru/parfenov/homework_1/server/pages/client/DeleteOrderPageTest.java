package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class DeleteOrderPageTest {
    @Test
    public void test_admin_deletes_order_successfully() throws IOException {
        User admin = new User(1, UserRoles.ADMIN, "Admin", "password", "contact", 0);
        OrderService orderService = mock(OrderService.class);
        Order order = new Order(1, admin, 1, OrderType.SERVICE, OrderStatus.OPEN);
        when(orderService.findById(1)).thenReturn(order);
        DeleteOrderPage deleteOrderPage = new DeleteOrderPage(admin, orderService);
        BufferedReader reader = mock(BufferedReader.class);
        deleteOrderPage.reader = reader;
        when(reader.readLine()).thenReturn("1", "0");
        deleteOrderPage.run();
        verify(orderService).delete(order);
    }
}