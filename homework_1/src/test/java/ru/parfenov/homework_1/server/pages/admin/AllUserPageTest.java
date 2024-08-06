package ru.parfenov.homework_1.server.pages.admin;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.service.UserService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AllUserPageTest {
    @Test
    public void test_findAll_method_called() {
        UserService mockService = mock(UserService.class);
        AllUserPage allUserPage = new AllUserPage(mockService);
        allUserPage.run();
        verify(mockService).findAll();
    }

    @Test
    public void test_run_method_calls_findAll() {
        UserService mockService = mock(UserService.class);
        AllUserPage allUserPage = new AllUserPage(mockService);
        allUserPage.run();
        verify(mockService).findAll();
    }

    @Test
    public void test_run_method_with_null_service() {
        UserService nullService = null;
        AllUserPage allUserPage = new AllUserPage(null);
        assertThrows(NullPointerException.class, allUserPage::run);
    }
}