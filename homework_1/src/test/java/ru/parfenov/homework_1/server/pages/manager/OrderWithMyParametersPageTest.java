package ru.parfenov.homework_1.server.pages.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

@DisplayName("Тестирование страницы ордера по заданным параметрам")
public class OrderWithMyParametersPageTest {

    @Test
    @DisplayName("Корректная печать успешного поиска")
    public void test_correct_input_successful_execution() throws IOException {
        OrderService service = mock(OrderService.class);
        OrderWithMyParametersPage page = new OrderWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "2", "3", "0", "0");

        page.run();

        verify(service).findByParameter(1, 2, 3, OrderType.BUY, OrderStatus.OPEN);
    }

    @Test
    @DisplayName("Если при поиске не все поля заполнены")
    public void test_user_inputs_zero_for_type_and_status() throws IOException {
        OrderService service = mock(OrderService.class);
        OrderWithMyParametersPage page = new OrderWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "2", "3", "0", "0");

        page.run();

        verify(service).findByParameter(1, 2, 3, OrderType.BUY, OrderStatus.OPEN);
    }

    @Test
    @DisplayName("Корректная печать успешного поиска для сервиса")
    public void test_user_inputs_non_zero_for_type_and_status() throws IOException {
        OrderService service = mock(OrderService.class);
        OrderWithMyParametersPage page = new OrderWithMyParametersPage(service);
        BufferedReader reader = mock(BufferedReader.class);
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "2", "3", "1", "1");

        page.run();

        verify(service).findByParameter(1, 2, 3, OrderType.SERVICE, OrderStatus.CLOSED);
    }
}