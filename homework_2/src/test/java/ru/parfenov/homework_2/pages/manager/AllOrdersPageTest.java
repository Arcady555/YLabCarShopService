package ru.parfenov.homework_2.pages.manager;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.homework_2.service.OrderService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AllOrdersPageTest {

    @Test
    @DisplayName("Вызов метода run()")
    public void test_run_calls_findAll() {
        OrderService service = mock(OrderService.class);
        AllOrdersPage page = new AllOrdersPage(service);
        page.run();
        verify(service).findAll();
    }
}