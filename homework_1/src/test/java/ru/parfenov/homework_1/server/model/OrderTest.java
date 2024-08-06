package ru.parfenov.homework_1.server.model;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.enums.UserRoles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderTest {
    @Test
    public void test_create_order_with_valid_parameters() {
        User author = new User(1, UserRoles.CLIENT, "John Doe", "password123", "john.doe@example.com", 5);
        Order order = new Order(1, author, 101, OrderType.BUY, OrderStatus.OPEN);

        assertEquals(1, order.getId());
        assertEquals(author, order.getAuthor());
        assertEquals(101, order.getCarId());
        assertEquals(OrderType.BUY, order.getType());
        assertEquals(OrderStatus.OPEN, order.getStatus());
    }

    @Test
    public void test_create_order_with_null_author() {
        Order order = new Order(2, null, 102, OrderType.SERVICE, OrderStatus.CLOSED);

        assertEquals(2, order.getId());
        assertNull(order.getAuthor());
        assertEquals(102, order.getCarId());
        assertEquals(OrderType.SERVICE, order.getType());
        assertEquals(OrderStatus.CLOSED, order.getStatus());
    }
}