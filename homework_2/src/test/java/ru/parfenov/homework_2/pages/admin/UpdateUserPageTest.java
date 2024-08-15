package ru.parfenov.homework_2.pages.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class UpdateUserPageTest {
    UserService service = mock(UserService.class);
    UpdateUserPage updateUserPage = new UpdateUserPage(service);
    User user = new User(1, UserRole.CLIENT, "John Doe", "password", "contact", 5);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Удаление юзера")
    public void user_is_deleted_successfully_when_0_is_entered() throws IOException, InterruptedException {
        when(service.findByIdForAdmin(1)).thenReturn(user);
        updateUserPage.reader = reader;
        when(reader.readLine()).thenReturn("1", "0");
        updateUserPage.run();
        verify(service).delete(1);
    }

    @Test
    @DisplayName("Обновление имени юзера")
    public void user_name_is_updated_successfully_when_0_is_entered_and_new_name_provided() throws IOException, InterruptedException {
        when(service.findByIdForAdmin(1)).thenReturn(user);
        updateUserPage.reader = reader;
        when(reader.readLine()).thenReturn("1", "1", "1", "0", "New Name");
        updateUserPage.run();
        verify(service).update(1, null, "New Name", "", "", 0);
    }
}