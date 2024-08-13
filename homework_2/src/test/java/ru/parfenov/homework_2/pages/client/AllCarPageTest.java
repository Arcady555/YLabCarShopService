package ru.parfenov.homework_2.pages.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.service.CarService;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;


public class AllCarPageTest {

    @Test
    @DisplayName("Корректный вызов страницы")
    public void test_allcarpage_instantiated_with_valid_carservice() {
        CarService carService = mock(CarService.class);
        AllCarPage allCarPage = new AllCarPage(carService);
        assertNotNull(allCarPage);
    }

    @Test
    @DisplayName("Корректный вызов метода run()")
    public void test_run_executes_without_exceptions() {
        CarService carService = mock(CarService.class);
        AllCarPage allCarPage = new AllCarPage(carService);
        assertDoesNotThrow(() -> allCarPage.run());
    }
}
