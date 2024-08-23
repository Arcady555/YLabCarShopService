package ru.parfenov.homework_3.repository;

import lombok.Getter;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Инициализация контейнера и подключения для тест контейнеров
 */

@Getter
public class InitContainer {
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("y_lab_car_service")
            .withUsername("user")
            .withPassword("pass")
            .withInitScript("src/main/resources/db/changelog/01_ddl_create_tables.xml")
            .withInitScript("src/main/resources/db/changelog/02_dml_insert_admin_into_users.xml");
    private final Connection connection = DriverManager.getConnection(
            postgreSQLContainer.getJdbcUrl(),
            postgreSQLContainer.getUsername(),
            postgreSQLContainer.getPassword());


    public InitContainer() throws SQLException {
    }
}
