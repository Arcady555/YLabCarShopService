package ru.parfenov.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.parfenov.dto.CarDTO;
import ru.parfenov.dto.CarDTOMapper;
import ru.parfenov.enums.CarCondition;
import ru.parfenov.model.Car;
import ru.parfenov.model.LineInLog;
import ru.parfenov.service.CarService;
import ru.parfenov.service.LogService;
import ru.parfenov.service.PersonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CarController.class)
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;
    @MockBean
    private CarDTOMapper carDTOMapper;

    @Test
    public void test_find_car_by_id() {
        CarService carService = mock(CarService.class);
        CarController carController = new CarController(carService, carDTOMapper);
        Car car = new Car(1, 1, "Model", "Brand", 2020, 1000000, CarCondition.NEW);
        when(carService.findById(1))
                .thenReturn(Optional.of(car));

        ResponseEntity<CarDTO> response = carController.viewCar(1);
        CarDTO carDTO = carDTOMapper.toCarDTO(car);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(carDTO, response.getBody());
    }

}