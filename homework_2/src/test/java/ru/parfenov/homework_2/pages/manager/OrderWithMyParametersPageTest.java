package ru.parfenov.homework_2.pages.manager;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;
import ru.parfenov.homework_2.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class OrderWithMyParametersPageTest {
    OrderService service = mock(OrderService.class);
    OrderWithMyParametersPage page = new OrderWithMyParametersPage(service);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_successful_reading_of_inputs() throws IOException, InterruptedException {
        when(reader.readLine()).thenReturn("1", "2", "0", "0");
        page.reader = reader;
        page.run();
        verify(service).findByParameter(1, 2, OrderType.BUY, OrderStatus.OPEN);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_mapping_of_user_input_to_enums() throws IOException, InterruptedException {
        when(reader.readLine()).thenReturn("1", "2", "0", "1");
        page.reader = reader;
        page.run();
        verify(service).findByParameter(1, 2, OrderType.BUY, OrderStatus.CLOSED);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_valid_parameters() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("1", "2", "0", "0");
        page.run();
        verify(service).findByParameter(1, 2, OrderType.BUY, OrderStatus.OPEN);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_empty_input_for_type_and_status() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("5", "6", "", "");
        page.run();
        verify(service).findByParameter(5, 6, null, null);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_invalid_string_for_order_type_or_status() throws IOException, InterruptedException {
        when(reader.readLine()).thenReturn("1", "2", "invalid", "invalid");
        page.reader = reader;
        page.run();
        verify(service).findByParameter(1, 2, OrderType.SERVICE, OrderStatus.CLOSED);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_parse_buy_order_type() throws IOException, InterruptedException {
        page.reader = reader;
        when(reader.readLine()).thenReturn("7", "8", "0", "");
        page.run();
        verify(service).findByParameter(7, 8, OrderType.BUY, null);
    }
}