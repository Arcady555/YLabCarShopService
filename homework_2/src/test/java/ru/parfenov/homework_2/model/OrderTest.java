package ru.parfenov.homework_2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.OrderStatus;
import ru.parfenov.homework_2.enums.OrderType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    @DisplayName("Создание заказа со всеми полями")
    public void test_order_creation_with_all_fields() {
        Order order = new Order(1, 2, 3, OrderType.BUY, OrderStatus.OPEN);
        assertEquals(1, order.getId());
        assertEquals(2, order.getAuthorId());
        assertEquals(3, order.getCarId());
        assertEquals(OrderType.BUY, order.getType());
        assertEquals(OrderStatus.OPEN, order.getStatus());
    }

    @Test
    @DisplayName("Проверка геттера и сеттера")
    public void test_order_getters_and_setters() {
        Order order = new Order();
        order.setId(1);
        order.setAuthorId(2);
        order.setCarId(3);
        order.setType(OrderType.SERVICE);
        order.setStatus(OrderStatus.CLOSED);
        assertEquals(1, order.getId());
        assertEquals(2, order.getAuthorId());
        assertEquals(3, order.getCarId());
        assertEquals(OrderType.SERVICE, order.getType());
        assertEquals(OrderStatus.CLOSED, order.getStatus());
    }

    @Test
    @DisplayName("Проверка toString()")
    public void test_order_to_string_format() {
        Order order = new Order(1, 2, 3, OrderType.BUY, OrderStatus.OPEN);
        String expected = "id: 1, authorId: 2, car id: 3, type: buy, status: open.";
        assertEquals(expected, order.toString());
    }

    @Test
    @DisplayName("Проверка значений order type")
    public void test_order_type_enum_values() {
        assertEquals("buy", OrderType.BUY.toString());
        assertEquals("service", OrderType.SERVICE.toString());
    }

    @Test
    @DisplayName("Создание заказа с пустым конструктором")
    public void test_order_creation_with_default_constructor() {
        Order order = new Order();
        assertNotNull(order);
    }

    @Test
    @DisplayName("Создание заказа с нулл типом")
    public void test_order_creation_with_null_order_type() {
        Order order = new Order(1, 2, 3, null, OrderStatus.OPEN);
        assertNull(order.getType());
        assertEquals(OrderStatus.OPEN, order.getStatus());
    }

    @Test
    @DisplayName("Создание заказа с нулл статусом)")
    public void test_order_creation_with_null_order_status() {
        Order order = new Order(1, 2, 3, OrderType.BUY, null);
        assertNull(order.getStatus());
        assertEquals(OrderType.BUY, order.getType());
    }

    @Test
    @DisplayName("Создание с негативными полями")
    public void test_order_creation_with_negative_id_values() {
        Order order = new Order(-1, -2, -3, OrderType.SERVICE, OrderStatus.CLOSED);
        assertEquals(-1, order.getId());
        assertEquals(-2, order.getAuthorId());
        assertEquals(-3, order.getCarId());
        assertEquals(OrderType.SERVICE, order.getType());
        assertEquals(OrderStatus.CLOSED, order.getStatus());
    }
}