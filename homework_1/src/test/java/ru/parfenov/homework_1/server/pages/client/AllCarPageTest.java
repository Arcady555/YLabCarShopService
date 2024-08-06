package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AllCarPageTest {
    @Test
    public void test_allcarpage_runs_successfully() throws IOException, InterruptedException {
        CarService carService = mock(CarService.class);
        AllCarPage allCarPage = new AllCarPage(carService);
        allCarPage.run();
        verify(carService, times(1)).findAll();
    }

    @Test
    public void test_allcarpage_unexpected_runtime_exception() throws IOException, InterruptedException {
        CarService carService = mock(CarService.class);
        doThrow(RuntimeException.class).when(carService).findAll();
        AllCarPage allCarPage = new AllCarPage(carService);
        assertThrows(RuntimeException.class, () -> allCarPage.run());
    }

    @Test
    public void test_allcarpage_run_multiple_times() throws IOException, InterruptedException {
        CarService carService = mock(CarService.class);
        AllCarPage allCarPage = new AllCarPage(carService);
        allCarPage.run();
        allCarPage.run();
        verify(carService, times(2)).findAll();
    }
}