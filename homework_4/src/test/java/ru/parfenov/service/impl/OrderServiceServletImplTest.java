package ru.parfenov.service.impl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;
import ru.parfenov.model.Order;
import ru.parfenov.repository.OrderRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceServletImplTest {
    OrderRepository repo = mock(OrderRepository.class);
    OrderServiceServletImpl service = new OrderServiceServletImpl(repo);

    @Test
    @DisplayName("Создать заказ с валидными данными")
    public void test_create_order_with_valid_inputs() {
        Order order = new Order(0, 1, 1, OrderType.BUY, OrderStatus.OPEN);
        when(repo.create(any(Order.class))).thenReturn(order);
        Optional<Order> result = service.create(1, 1, "buy");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(order, result.get());
    }

    @Test
    @DisplayName(("Проверка метода findById"))
    public void test_find_order_by_id_exists() {
        Order order = new Order(1, 1, 1, OrderType.BUY, OrderStatus.OPEN);
        when(repo.findById(1)).thenReturn(order);
        Optional<Order> result = service.findById(1);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(order, result.get());
    }

    @Test
    @DisplayName("Проверка isOwnOrder")
    public void test_is_own_order_by_id_true() {
        Order order = new Order(1, 1, 1, OrderType.BUY, OrderStatus.OPEN);
        when(repo.findById(1)).thenReturn(order);
        boolean result = service.isOwnOrder(1, 1);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName(("Закрыть заказ"))
    public void test_close_order_success() {
        when(repo.update(1)).thenReturn(true);
        boolean result = service.close(1);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Создание заказа с невалидными данными")
    public void test_create_order_with_invalid_type() {
        Optional<Order> result = service.create(1, 1, "invalid");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Поиск с несуществующим ID")
    public void test_find_order_by_non_existent_id() {
        when(repo.findById(999)).thenReturn(null);
        Optional<Order> result = service.findById(999);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Закрыть заказ которого нет")
    public void test_close_order_non_existent_id() {
        when(repo.update(999)).thenReturn(false);
        boolean result = service.close(999);
        Assertions.assertFalse(result);
    }
}