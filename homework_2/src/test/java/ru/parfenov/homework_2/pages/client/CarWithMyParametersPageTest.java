package ru.parfenov.homework_2.pages.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.CarCondition;
import ru.parfenov.homework_2.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;


public class CarWithMyParametersPageTest {
    CarService carService = mock(CarService.class);
    CarWithMyParametersPage page = new CarWithMyParametersPage(carService);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_successfully_reads_and_processes_all_user_inputs() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("1", "Toyota", "Camry", "2020", "10000", "20000", "0");

        page.run();

        verify(carService).findByParameter(1, "Toyota", "Camry", 2020, 10000, 20000, CarCondition.NEW);
    }

    @Test
    @DisplayName("Соответствие введённых не пустых значений и параметров для методов нижнего слоя")
    public void test_correctly_identifies_and_sets_car_condition() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("3", "Ford", "Focus", "2015", "7000", "12000", "another");

        page.run();

        verify(carService).findByParameter(3, "Ford", "Focus", 2015, 7000, 12000, CarCondition.USED);
    }

    @Test
    @DisplayName("Соответствие введённых значений и параметров для методов нижнего слоя")
    public void test_handles_empty_input_for_optional_parameters() throws IOException, InterruptedException {
        page.reader = reader;

        when(reader.readLine()).thenReturn("4", "", "", "", "", "", "");

        page.run();

        verify(carService).findByParameter(4, "", "", 0, 0, 0, null);
    }
}