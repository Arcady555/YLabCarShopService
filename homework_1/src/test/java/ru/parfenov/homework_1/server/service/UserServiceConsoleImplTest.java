package ru.parfenov.homework_1.server.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;
import ru.parfenov.homework_1.server.store.UserStore;
import ru.parfenov.homework_1.server.utility.Utility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса юзеров")
public class UserServiceConsoleImplTest {

    @Test
    @DisplayName("Обновление и печать")
    public void update_user_should_store_and_print_details() {
        UserStore store = mock(UserStore.class);
        UserServiceConsoleImpl service = new UserServiceConsoleImpl(store);
        User user = new User(1, UserRoles.CLIENT, "client", "password", "contact", 0);
        service.update(user);
        verify(store).update(user);
        mockStatic(Utility.class);
        Utility.printUser(user);
    }

    @Test
    @DisplayName("Обновление несуществующего юзера")
    public void update_non_existent_user_should_handle_error_gracefully() {
        UserStore store = mock(UserStore.class);
        UserServiceConsoleImpl service = new UserServiceConsoleImpl(store);
        doThrow(new IllegalArgumentException("User not found")).when(store).update(any(User.class));
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(new User(1, UserRoles.CLIENT, "client", "password", "contact", 0));
        });
    }

    @Test
    @DisplayName("Удаление несуществующего юзера")
    public void delete_non_existent_user_should_handle_error_gracefully() {
        UserStore store = mock(UserStore.class);
        UserServiceConsoleImpl service = new UserServiceConsoleImpl(store);
        when(store.delete(any(User.class))).thenReturn(null);
        assertNull(service.delete(new User(1, UserRoles.CLIENT, "client", "password", "contact", 0)));
    }
}