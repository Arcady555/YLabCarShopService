package ru.parfenov.homework_2.pages.manager;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_2.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderPageTest {
    OrderService mockService = mock(OrderService.class);
    OrderPage orderPage = new OrderPage(mockService);
    BufferedReader mockReader = mock(BufferedReader.class);

    @Test
    @DisplayName("Соответствие введённого значения и параметра метода слоя сервис")
    public void test_read_and_parse_valid_integer_input() throws IOException, InterruptedException {
        when(mockReader.readLine()).thenReturn("123");
        int result = orderPage.checkIfReadInt(mockReader.readLine());
        assertEquals(123, result);
    }

    @Test
    @DisplayName("Соответствие введённого значения и параметра метода слоя сервис")
    public void test_find_order_by_id() throws IOException, InterruptedException {
        when(mockReader.readLine()).thenReturn("123");
        orderPage.reader = mockReader;
        orderPage.run();
        verify(mockService).findById(123);
    }

    @Test
    @DisplayName("Если отправить не пустую строку")
    public void test_handle_valid_integer_input() throws IOException, InterruptedException {
        int result = orderPage.checkIfReadInt("123");
        assertEquals(123, result);
    }

    @Test
    @DisplayName("Если отправить пустую строку")
    public void test_handle_empty_input_string_gracefully() throws IOException, InterruptedException {
        when(mockReader.readLine()).thenReturn("");
        int result = orderPage.checkIfReadInt(mockReader.readLine());
        assertEquals(0, result);
    }
}