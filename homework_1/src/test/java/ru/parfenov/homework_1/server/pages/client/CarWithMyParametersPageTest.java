package ru.parfenov.homework_1.server.pages.client;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.CarCondition;
import ru.parfenov.homework_1.server.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CarWithMyParametersPageTest {

    @Test
    public void test_valid_inputs() throws IOException {
        CarService carService = mock(CarService.class);
        CarWithMyParametersPage page = new CarWithMyParametersPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1", "2", "Toyota", "Camry", "2020", "10000", "20000", "0");
        page.reader = reader;
        page.run();
        verify(carService).findById(1);
        verify(carService).findByParameter(1, 2, "Toyota", "Camry", 2020, 10000, 20000, CarCondition.NEW);
    }

    @Test
    public void test_find_by_id() throws IOException {
        CarService carService = mock(CarService.class);
        CarWithMyParametersPage page = new CarWithMyParametersPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1");
        page.reader = reader;
        page.run();
        verify(carService).findById(1);
    }

    @Test
    public void test_find_by_parameter() throws IOException {
        CarService carService = mock(CarService.class);
        CarWithMyParametersPage page = new CarWithMyParametersPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1", "2", "Toyota", "Camry", "2020", "10000", "20000", "0");
        page.reader = reader;
        page.run();
        verify(carService).findByParameter(1, 2, "Toyota", "Camry", 2020, 10000, 20000, CarCondition.NEW);
    }

    @Test
    public void test_condition_new() throws IOException {
        CarService carService = mock(CarService.class);
        CarWithMyParametersPage page = new CarWithMyParametersPage(carService);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1", "2", "Toyota", "Camry", "2020", "10000", "20000", "0");
        page.reader = reader;
        page.run();
        verify(carService).findByParameter(1, 2, "Toyota", "Camry", 2020, 10000, 20000, CarCondition.NEW);
    }
}