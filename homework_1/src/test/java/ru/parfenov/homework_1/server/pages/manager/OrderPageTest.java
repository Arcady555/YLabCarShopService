package ru.parfenov.homework_1.server.pages.manager;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderPageTest {

    @Test
    public void test_valid_order_id_found() throws IOException {
        OrderService service = mock(OrderService.class);
        OrderPage orderPage = new OrderPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("123");
        orderPage.reader = reader;
        orderPage.run();
        verify(service).findById(123);
    }

    @Test
    public void test_valid_order_id_service_returns_details() throws IOException {
        OrderService service = mock(OrderService.class);
        Order order = new Order();
        when(service.findById(123)).thenReturn(order);
        OrderPage orderPage = new OrderPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("123");
        orderPage.reader = reader;
        orderPage.run();
        verify(service).findById(123);
    }

    @Test
    public void test_order_page_initialization() {
        OrderService service = mock(OrderService.class);
        OrderPage orderPage = new OrderPage(service);
        assertNotNull(orderPage);
    }
}