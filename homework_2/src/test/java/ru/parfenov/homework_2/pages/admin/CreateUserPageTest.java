package ru.parfenov.homework_2.pages.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.parfenov.homework_2.enums.UserRole;
import ru.parfenov.homework_2.model.User;
import ru.parfenov.homework_2.service.LogService;
import ru.parfenov.homework_2.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class CreateUserPageTest {
    UserService userService = mock(UserService.class);
    LogService logService = mock(LogService.class);
    CreateUserPage createUserPage = new CreateUserPage(userService, logService);
    BufferedReader reader = mock(BufferedReader.class);

    @Test
    @DisplayName("Создание юзера с ролью admin")
    public void test_create_user_with_role_admin() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("0", "adminName", "adminPass", "adminContact", "5");
        User user = new User(0, UserRole.ADMIN, "adminName", "adminPass", "adminContact", 5);
        when(userService.createByAdmin(anyInt(), eq(UserRole.ADMIN), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(userService).createByAdmin(0, UserRole.ADMIN, "adminName", "adminPass", "adminContact", 5);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(0), eq("registration by admin"));
    }

    @Test
    @DisplayName("Создание юзера с ролью manager")
    public void test_create_user_with_role_manager() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("1", "managerName", "managerPass", "managerContact", "3");
        User user = new User(0, UserRole.MANAGER, "managerName", "managerPass", "managerContact", 3);
        when(userService.createByAdmin(anyInt(), eq(UserRole.MANAGER), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(userService).createByAdmin(0, UserRole.MANAGER, "managerName", "managerPass", "managerContact", 3);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(0), eq("registration by admin"));
    }

    @Test
    @DisplayName("Создание юзера")
    public void test_create_user_with_role_client() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("2", "clientName", "clientPass", "clientContact", "1");
        User user = new User(0, UserRole.CLIENT, "clientName", "clientPass", "clientContact", 1);
        when(userService.createByAdmin(anyInt(), eq(UserRole.CLIENT), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(userService).createByAdmin(0, UserRole.CLIENT, "clientName", "clientPass", "clientContact", 1);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(0), eq("registration by admin"));
    }

    @Test
    @DisplayName("Создание юзера")
    public void admin_creates_user_with_role_client() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("2", "clientName", "clientPass", "clientContact", "5");
        User user = new User(0, UserRole.CLIENT, "clientName", "clientPass", "clientContact", 5);
        when(userService.createByAdmin(anyInt(), eq(UserRole.CLIENT), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(userService).createByAdmin(0, UserRole.CLIENT, "clientName", "clientPass", "clientContact", 5);
        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(0), eq("registration by admin"));
    }

    @Test
    @DisplayName("Проверка лога")
    public void log_entry_created_on_user_creation() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("0", "userName", "userPass", "userContact", "5");
        User user = new User(0, UserRole.ADMIN, "userName", "userPass", "userContact", 5);
        when(userService.createByAdmin(anyInt(), eq(UserRole.ADMIN), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(0), eq("registration by admin"));
    }

    @Test
    @DisplayName("Ввод валидных данных")
    public void test_user_creation_logs_saved_correctly() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("0", "logName", "logPass", "logContact", "2");
        User user = new User(0, UserRole.ADMIN, "logName", "logPass", "logContact", 2);
        when(userService.createByAdmin(anyInt(), eq(UserRole.ADMIN), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(logService).saveLineInLog(any(LocalDateTime.class), eq(0), eq("registration by admin"));
    }

    @Test
    @DisplayName("Ввод данных с пустым полем name")
    public void test_empty_name_input_handled_gracefully() throws IOException, InterruptedException {
        createUserPage.reader = reader;

        when(reader.readLine()).thenReturn("0", "", "validPass", "validContact", "4");
        User user = new User(0, UserRole.ADMIN, "", "validPass", "validContact", 4);
        when(userService.createByAdmin(anyInt(), eq(UserRole.ADMIN), anyString(), anyString(), anyString(), anyInt())).thenReturn(user);

        createUserPage.run();

        verify(userService).createByAdmin(0, UserRole.ADMIN, "", "validPass", "validContact", 4);
    }
}