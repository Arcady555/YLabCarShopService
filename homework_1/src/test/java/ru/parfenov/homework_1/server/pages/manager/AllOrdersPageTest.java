package ru.parfenov.homework_1.server.pages.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование страницы со списком ордеров")
public class AllOrdersPageTest {

    @Test
    @DisplayName("Если пустой список")
    public void test_initialization_with_valid_service() {
        OrderService service = mock(OrderService.class);
        AllOrdersPage page = new AllOrdersPage(service);
        assertNotNull(page);
    }

    @Test
    @DisplayName("Если список не пустой")
    public void test_run_calls_findAll() {
        OrderService service = mock(OrderService.class);
        AllOrdersPage page = new AllOrdersPage(service);
        page.run();
        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("Проверка на выброс NPE")
    public void test_run_with_null_service() {
        OrderService service = null;
        AllOrdersPage page = new AllOrdersPage(service);
        assertThrows(NullPointerException.class, () -> {
            page.run();
        });
    }

    @Test
    @DisplayName("Проверка на RTE")
    public void test_findAll_throws_exception() {
        OrderService service = mock(OrderService.class);
        doThrow(new RuntimeException()).when(service).findAll();
        AllOrdersPage page = new AllOrdersPage(service);
        assertThrows(RuntimeException.class, () -> {
            page.run();
        });
    }

    @Test
    @DisplayName("Неоднократный вызов")
    public void test_run_called_multiple_times() {
        OrderService service = mock(OrderService.class);
        AllOrdersPage page = new AllOrdersPage(service);
        page.run();
        page.run();
        verify(service, times(2)).findAll();
    }
}