package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CarPageTest {

    @Test
    public void test_valid_car_id() throws IOException {
        CarService carService = mock(CarService.class);
        CarPage carPage = new CarPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("123");
        carPage.reader = reader;
        carPage.run();
        verify(carService).findById(123);
    }

    @Test
    public void test_prompt_and_read_input() throws IOException {
        CarService carService = mock(CarService.class);
        CarPage carPage = new CarPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("123");
        carPage.reader = reader;
        carPage.run();
        verify(reader).readLine();
    }

    @Test
    public void test_negative_car_id() throws IOException {
        CarService carService = mock(CarService.class);
        CarPage carPage = new CarPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("-1");
        carPage.reader = reader;
        carPage.run();
        verify(carService).findById(-1);
    }

    @Test
    public void test_non_existent_car_id() throws IOException {
        CarService carService = mock(CarService.class);
        when(carService.findById(999)).thenReturn(null);
        CarPage carPage = new CarPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("999");
        carPage.reader = reader;
        carPage.run();
        verify(carService).findById(999);
    }

    @Test
    public void test_extremely_large_car_id() throws IOException {
        CarService carService = mock(CarService.class);
        CarPage carPage = new CarPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("999999999");
        carPage.reader = reader;
        carPage.run();
        verify(carService).findById(999999999);
    }
}