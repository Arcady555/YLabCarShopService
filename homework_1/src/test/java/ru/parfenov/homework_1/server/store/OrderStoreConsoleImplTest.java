package ru.parfenov.homework_1.server.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.Order;
import ru.parfenov.homework_1.server.model.User;

import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тестирование хранилища заказов")
public class OrderStoreConsoleImplTest {

    @Test
    @DisplayName("Удаление заказа")
    public void test_delete_order_removes_from_orderMap() {
        OrderStoreConsoleImpl store = new OrderStoreConsoleImpl();
        User author = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(0, author, 1, OrderType.BUY, OrderStatus.OPEN);

        store.create(order);
        store.delete(order);

        assertNull(store.findById(1));
    }

    @Test
    @DisplayName("Поиск заказа по несуществующему ID")
    public void test_find_order_by_nonexistent_id() {
        OrderStoreConsoleImpl store = new OrderStoreConsoleImpl();

        assertNull(store.findById(999));
    }

    @Test
    @DisplayName("Обновление несуществующего заказа")
    public void test_update_nonexistent_order() {
        OrderStoreConsoleImpl store = new OrderStoreConsoleImpl();
        User author = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(999, author, 1, OrderType.BUY, OrderStatus.OPEN);

        assertNull(store.update(order));
    }

    @Test
    @DisplayName("Удаление несуществующего заказа")
    public void test_delete_nonexistent_order() {
        OrderStoreConsoleImpl store = new OrderStoreConsoleImpl();
        User author = new User(1, UserRoles.CLIENT, "John Doe", "password", "contact", 0);
        Order order = new Order(999, author, 1, OrderType.BUY, OrderStatus.OPEN);

        assertNull(store.delete(order));
    }
}