package ru.homework_5.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import ru.homework_5.dto.OrderDTO;
import ru.homework_5.dto.OrderDTOMapper;
import ru.homework_5.enums.OrderStatus;
import ru.homework_5.enums.OrderType;
import ru.homework_5.enums.Role;
import ru.homework_5.model.Order;
import ru.homework_5.model.Person;
import ru.homework_5.repository.OrderRepository;
import ru.homework_5.service.CarService;
import ru.homework_5.service.PersonService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class OrderServiceSpringImplTest {
    @Autowired
    OrderDTOMapper dtoMapper;
    OrderRepository repo = mock(OrderRepository.class);
    PersonService personService = mock(PersonService.class);
    CarService carService = mock(CarService.class);
    OrderServiceSpringImpl orderService = new OrderServiceSpringImpl(repo, personService, carService);

    @Test
    @WithMockUser
    @DisplayName("Метод create костыль")
    void create() {
        Order order = new Order(1, 1, 1, OrderType.BUY, OrderStatus.OPEN);

        OrderDTO orderDTO = dtoMapper.toOrderDTO(order);
        when(repo.save(any(Order.class))).thenReturn(order);
        when(carService.isOwnCar(1, 1)).thenReturn(false);

        Optional<Order> result = orderService.create(orderDTO);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(order, result.get());
    }

    @Test
    @DisplayName("Метод findById")
    void findById() {
        Order order = new Order(1, 1, 1, OrderType.BUY, OrderStatus.OPEN);
        when(repo.findById(1)).thenReturn(Optional.of(order));
        Optional<Order> result = orderService.findById(1);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(order, result.get());
    }

    @Test
    @WithMockUser
    @DisplayName("Метод isOwnOrder")
    void isOwnOrder() {
        Person person1 = new Person(3, Role.CLIENT, "Arcady", "1", "Contact1", 0);
        Person person2 = new Person(4, Role.CLIENT, "notArcady", "1", "Contact2", 0);
        Order order = new Order(1, person1.getId(), 1, OrderType.SERVICE, OrderStatus.OPEN);
        when(repo.findById(order.getId())).thenReturn(Optional.of(order));
        boolean result1 = orderService.isOwnOrder(person1.getId(), order.getId());
        boolean result2 = orderService.isOwnOrder(person2.getId(), order.getId());
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }

    @Test
    @DisplayName("Метод close")
    void close() {
        Order order = new Order(1, 1, 1, OrderType.SERVICE, OrderStatus.OPEN);
        when(repo.findById(order.getId())).thenReturn(Optional.of(order));
        orderService.close(1);
        Assertions.assertEquals(repo.findById(1).get().getStatus(), OrderStatus.CLOSED);
    }

    @Test
    @WithMockUser
    @DisplayName("Метод delete")
    void delete() {
        Order order = new Order(1, 1, 1, OrderType.SERVICE, OrderStatus.OPEN);
        when(repo.findById(order.getId())).thenReturn(Optional.of(order));
        orderService.delete(1);
        Assertions.assertFalse(repo.existsById(1));
    }

    @Test
    void findAll() {
        List<Order> list = List.of(new Order(1, 1, 1, OrderType.SERVICE, OrderStatus.OPEN),
                new Order(2, 1, 2, OrderType.SERVICE, OrderStatus.OPEN));
        when(repo.findAll()).thenReturn(list);
        List<Order> result = orderService.findAll();
        Assertions.assertEquals(result, list);
    }

    @Test
    void findByParameter() {
        List<Order> list = List.of(new Order(1, 1, 1, OrderType.SERVICE, OrderStatus.OPEN),
                new Order(2, 1, 2, OrderType.SERVICE, OrderStatus.OPEN));
        when(repo.findAll()).thenReturn(list);
        List<Order> result = orderService.findAll();
        Assertions.assertEquals(result, list);
    }
}