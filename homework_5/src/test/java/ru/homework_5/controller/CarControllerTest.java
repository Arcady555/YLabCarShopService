package ru.homework_5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.homework_5.Main;
import ru.homework_5.dto.CarDTO;
import ru.homework_5.dto.CarDTOMapper;
import ru.homework_5.dto.CarDTOMapperImpl;
import ru.homework_5.model.Car;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final CarDTO carDTO1 = new CarDTO();
    private static final CarDTO carDTO2 = new CarDTO();
    private static Car car1 = new Car();
    private static List<Car> carList;
    private static List<CarDTO> listDTO;

    @BeforeAll
    public static void init() throws Exception {
        CarDTOMapper carDTOMapper = new CarDTOMapperImpl();
        carDTO1.setOwnerId(1);
        carDTO1.setBrand("Toyota");
        carDTO1.setModel("Corolla");
        carDTO1.setYearOfProd(2020);
        carDTO1.setPrice(20000);
        carDTO1.setCondition("NEW");
        car1 = carDTOMapper.toCar(carDTO1);

        carDTO2.setOwnerId(1);
        carDTO2.setBrand("notToyota");
        carDTO2.setModel("notCorolla");
        carDTO2.setYearOfProd(2021);
        carDTO2.setPrice(25000);
        carDTO2.setCondition("USED");
        Car car2 = carDTOMapper.toCar(carDTO2);

        carList = List.of(car1, car2);
        listDTO = carDTOMapper.toListCarDTO(carList);
    }

    @WithMockUser
    @Test
    @DisplayName("Метод findById")
    public void test_find_car_by_id() throws Exception {
        mockMvc.perform(get("/cars/car/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    @DisplayName("Метод create")
    public void create_car_successfully() throws Exception {
        mockMvc.perform(post("/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(post("/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO2)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    @DisplayName("Метод findAll")
    public void find_all_successfully() throws Exception {
        create_car_successfully();
        mockMvc.perform(get(
                        "/cars/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    @DisplayName("Метод findByParameters")
    public void find_by_parameters_successfully() throws Exception {
        create_car_successfully();
        mockMvc.perform(get(
                        "/cars/find-by-parameters?" +
                                "ownerId=-1&&brand=Toyota&&model=&&yearOfProd=2020&&" +
                                "priceFrom=10000&&priceTo=30000&&condition=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    @DisplayName("Метод delete")
    public void delete_car_successfully() throws Exception {
        mockMvc.perform(delete("/cars/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Car is deleted"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}