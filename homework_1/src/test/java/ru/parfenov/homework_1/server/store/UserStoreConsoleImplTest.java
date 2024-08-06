package ru.parfenov.homework_1.server.store;

import org.junit.jupiter.api.Test;
import ru.parfenov.homework_1.server.enums.UserRoles;
import ru.parfenov.homework_1.server.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoreConsoleImplTest {

    @Test
    public void create_user_with_valid_details() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User user = new User("testUser", "password123");
        User createdUser = store.create(user);
        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getName());
        assertEquals("password123", createdUser.getPassword());
    }

    @Test
    public void find_user_by_valid_id() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User user = new User("testUser", "password123");
        User createdUser = store.create(user);
        User foundUser = store.findById(createdUser.getId());
        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
    }

    @Test
    public void update_existing_user_details() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User user = new User("testUser", "password123");
        User createdUser = store.create(user);
        createdUser.setName("updatedName");
        User updatedUser = store.update(createdUser);
        assertNotNull(updatedUser);
        assertEquals("updatedName", updatedUser.getName());
    }

    @Test
    public void delete_existing_user() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User user = new User("testUser", "password123");
        User createdUser = store.create(user);
        User deletedUser = store.delete(createdUser);
        assertNotNull(deletedUser);
        assertEquals(createdUser.getId(), deletedUser.getId());
    }

    @Test
    public void find_user_by_invalid_id() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User foundUser = store.findById(999);
        assertNull(foundUser);
    }

    @Test
    public void update_non_existent_user() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User nonExistentUser = new User(999, UserRoles.CLIENT, "nonExistent", "password123", "contactInfo", 0);
        assertNull(store.update(nonExistentUser));
    }

    @Test
    public void delete_non_existent_user() {
        UserStoreConsoleImpl store = new UserStoreConsoleImpl();
        User nonExistentUser = new User(999, UserRoles.CLIENT, "nonExistent", "password123", "contactInfo", 0);
        assertNull(store.delete(nonExistentUser));
    }
}