package ru.parfenov.homework_1.server.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.service.OrderServiceConsoleImpl;
import ru.parfenov.homework_1.server.store.OrderStore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса заказов")
public class OrderServiceConsoleImplTest {

    @Test
    @DisplayName("Поиск заказа по ID")
    public void find_existing_order_by_id() {
        OrderStore store = mock(OrderStore.class);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(1, user, 1, OrderType.BUY, OrderStatus.OPEN);
        when(store.findById(1)).thenReturn(order);

        OrderServiceConsoleImpl service = new OrderServiceConsoleImpl(store);
        Order foundOrder = service.findById(1);

        assertEquals(order, foundOrder);
        verify(store).findById(1);
    }

    @Test
    @DisplayName("Обновление заказа")
    public void update_existing_order() {
        OrderStore store = mock(OrderStore.class);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(1, user, 1, OrderType.BUY, OrderStatus.OPEN);

        OrderServiceConsoleImpl service = new OrderServiceConsoleImpl(store);
        service.update(order);

        verify(store).update(order);
    }

    @Test
    @DisplayName("Удаление заказа")
    public void delete_existing_order() {
        OrderStore store = mock(OrderStore.class);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(1, user, 1, OrderType.BUY, OrderStatus.OPEN);
        when(store.delete(order)).thenReturn(order);

        OrderServiceConsoleImpl service = new OrderServiceConsoleImpl(store);
        Order deletedOrder = service.delete(order);

        assertEquals(order, deletedOrder);
        verify(store).delete(order);
    }

    @Test
    @DisplayName("Поиск заказа по несуществующему ID")
    public void find_order_by_non_existent_id() {
        OrderStore store = mock(OrderStore.class);
        when(store.findById(999)).thenReturn(null);

        OrderServiceConsoleImpl service = new OrderServiceConsoleImpl(store);
        Order foundOrder = service.findById(999);

        assertNull(foundOrder);
        verify(store).findById(999);
    }

    @Test
    @DisplayName("Обновление несуществующего заказа")
    public void update_non_existent_order() {
        OrderStore store = mock(OrderStore.class);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(999, user, 1, OrderType.BUY, OrderStatus.OPEN);

        doThrow(new IllegalArgumentException("Order not found")).when(store).update(order);

        OrderServiceConsoleImpl service = new OrderServiceConsoleImpl(store);

        assertThrows(IllegalArgumentException.class, () -> {
            service.update(order);
        });

        verify(store).update(order);
    }

    @Test
    @DisplayName("Удаление несуществующего заказа")
    public void delete_non_existent_order() {
        OrderStore store = mock(OrderStore.class);
        User user = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(999, user, 1, OrderType.BUY, OrderStatus.OPEN);

        when(store.delete(order)).thenReturn(null);

        OrderServiceConsoleImpl service = new OrderServiceConsoleImpl(store);
        Order deletedOrder = service.delete(order);

        assertNull(deletedOrder);
        verify(store).delete(order);
    }
}