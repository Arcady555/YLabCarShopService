package ru.homework_5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.homework_5.dto.CarDTO;
import ru.homework_5.dto.CarDTOMapper;
import ru.homework_5.dto.CarDTOMapperImpl;
import ru.homework_5.model.Car;
import ru.homework_5.service.CarService;
import ru.homework_5.enums.CarCondition;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CarController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;
    @MockBean
    private CarDTOMapper carDTOMapper;

    @WithMockUser
    @Test
    @DisplayName("Метод findById")
    public void test_find_car_by_id() throws Exception {
        Car car = new Car(
                1, 1, "Model", "Brand", 2020, 1000000, CarCondition.NEW
        );
        CarDTOMapper carDTOMapper = new CarDTOMapperImpl();
        CarDTO carDTO = carDTOMapper.toCarDTO(car);
        when(carService.findById(1))
                .thenReturn(Optional.of(car));
        mockMvc.perform(get("/cars/car/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Пока костыль --Метод create")
    public void create_car_successfully() throws Exception {
        CarDTO carDTO = insertCarDTO();
        CarDTOMapper carDTOMapper = new CarDTOMapperImpl();
        Car car = carDTOMapper.toCar(carDTO);
        when(carService.create(
                carDTO.getId(),
                carDTO.getBrand(),
                carDTO.getModel(),
                carDTO.getYearOfProd(),
                carDTO.getPrice(),
                carDTO.getCondition()
        ))
                .thenReturn(Optional.of(car));
        mockMvc.perform(post("/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    @DisplayName("Пока костыль --Метод update")
    public void update_car_successfully() throws Exception {
        CarDTO carDTO = insertCarDTO();
        when(carService.update(carDTO)).thenReturn(true);
        mockMvc.perform(post("/cars/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    @DisplayName("Пока костыль --Метод delete")
    public void delete_car_successfully() throws Exception {
        when(carService.delete(1)).thenReturn(true);
        mockMvc.perform(delete("/cars/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Car is deleted"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @WithMockUser
    @Test
    @DisplayName("Пока костыль --Метод findByParameters")
    public void find_by_parameters_successfully() throws Exception {
        List<Car> list = insertListCar();
        CarDTOMapper carDTOMapper = new CarDTOMapperImpl();
        List<CarDTO> listDTO = carDTOMapper.toListCarDTO(list);
        when(carService.findByParameter(
                        "1",
                        "Toyota",
                        "Corolla",
                        "2020",
                        "10000",
                        "30000",
                        "NEW"
                )
        )
                .thenReturn(list);
        mockMvc.perform(get(
                        "/cars/find-by_parameters?" +
                                "ownerId=1&brand=Toyota&model=Corolla&yearOfProd=2020&" +
                                "priceFrom=10000&priceTo=30000&condition=NEW")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listDTO)))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    @WithMockUser
    @Test
    @DisplayName("Пока костыль --Метод findAll")
    public void find_all_successfully() throws Exception {
        List<Car> list = insertListCar();
        CarDTOMapper carDTOMapper = new CarDTOMapperImpl();
        List<CarDTO> listDTO = carDTOMapper.toListCarDTO(list);
        when(carService.findAll()
        )
                .thenReturn(list);
        mockMvc.perform(get(
                        "/cars/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listDTO)))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    private CarDTO insertCarDTO() {
        CarDTO carDTO = new CarDTO();
        carDTO.setOwnerId(1);
        carDTO.setBrand("Toyota");
        carDTO.setModel("Corolla");
        carDTO.setYearOfProd(2020);
        carDTO.setPrice(20000);
        carDTO.setCondition("NEW");
        return carDTO;
    }

    private List<Car> insertListCar() {
        Car car1 = new Car(
                1, 1, "Toyota", "Corolla", 2020, 15000, CarCondition.NEW
        );
        Car car2 = new Car(
                2, 1, "Toyota", "Corolla", 2020, 25000, CarCondition.NEW
        );
        return List.of(car1, car2);
    }
}