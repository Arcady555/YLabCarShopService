package ru.homework_5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.homework_5.Main;
import ru.homework_5.dto.OrderDTO;
import ru.homework_5.dto.OrderDTOMapper;
import ru.homework_5.dto.OrderDTOMapperImpl;
import ru.homework_5.model.Order;
import ru.homework_5.service.OrderService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private static OrderService orderService;
    private static final OrderDTO orderDTO1 = new OrderDTO();
    private static final OrderDTO orderDTO2 = new OrderDTO();
    private static Order order1 = new Order();
    private static Order order2 = new Order();
    private static List<Order> orderList;
    private static List<OrderDTO> listDTO;

    @BeforeAll
    public static void init() throws Exception {
        OrderDTOMapper orderDTOMapper = new OrderDTOMapperImpl();

        orderDTO1.setId(1);
        orderDTO1.setAuthorId(2);
        orderDTO1.setCarId(1);
        orderDTO1.setType("BUY");
        orderDTO1.setStatus("OPEN");
        order1 = orderDTOMapper.toOrder(orderDTO1);

        orderDTO2.setAuthorId(2);
        orderDTO2.setCarId(2);
        orderDTO2.setType("BUY");
        orderDTO2.setStatus("OPEN");
        order2 = orderDTOMapper.toOrder(orderDTO2);

        orderList = List.of(order1, order2);
        listDTO = orderDTOMapper.toOrderListDTO(orderList);
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    @DisplayName("Метод findById")
    public void test_find_order_by_id() throws Exception {
        when(orderService.findById(1)).thenReturn(Optional.of(order1));
        mockMvc.perform(get("/orders/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    @DisplayName("Метод create")
    public void create_order_successfully() throws Exception {
        when(orderService.create(orderDTO1)).thenReturn(Optional.of(order1));
        when(orderService.create(orderDTO2)).thenReturn(Optional.of(order2));
        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO2)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    @DisplayName("Метод create")
    public void create_order2_successfully() throws Exception {
        when(orderService.create(orderDTO2)).thenReturn(Optional.of(order2));
        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO2)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    @DisplayName("Метод findAll")
    public void find_all_successfully() throws Exception {
        when(orderService.findAll()).thenReturn(orderList);
        mockMvc.perform(get(
                        "/orders/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    @DisplayName("Метод findByParameters")
    public void find_by_parameters_successfully() throws Exception {
        when(orderService.findByParameter(
                        "2",
                        "-1",
                        "",
                        "OPEN"
                )
        )
                .thenReturn(orderList);
        mockMvc.perform(get(
                        "/orders/find-by-parameters?authorId=2&&carId=-1&&type=&&status=OPEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    @DisplayName("Метод delete")
    public void delete_order_successfully() throws Exception {
        when(orderService.delete(2)).thenReturn(true);
        mockMvc.perform(delete("/orders/delete/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Order is deleted"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}