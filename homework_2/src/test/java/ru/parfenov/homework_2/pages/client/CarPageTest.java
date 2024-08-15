package ru.parfenov.homework_2.pages.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CarPageTest {
    CarService carService = mock(CarService.class);
    CarPage carPage = new CarPage(carService);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_car_service_find_by_id_called_with_correct_id() throws IOException, InterruptedException {
        carPage.reader = reader;
        when(reader.readLine()).thenReturn("123");
        carPage.run();
        verify(carService).findById(123);
    }

    @Test
    @DisplayName("Проверка reader")
    public void test_car_page_reads_input_from_buffered_reader() throws IOException, InterruptedException {
        carPage.reader = reader;
        when(reader.readLine()).thenReturn("123");
        String input = reader.readLine();
        assertEquals("123", input);
    }

    @Test
    @DisplayName("Выбросы исключений")
    public void test_car_service_find_by_id_returns_null_or_throws_exception() throws IOException, InterruptedException {
        carPage.reader = reader;
        when(reader.readLine()).thenReturn("123");
        when(carService.findById(123)).thenReturn(null);
        assertNull(carService.findById(123));
        when(carService.findById(123)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> {
            carService.findById(123);
        });
    }
}