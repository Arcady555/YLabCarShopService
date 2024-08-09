package ru.parfenov.homework_2.server.enums;

public enum UserRole {
    ADMIN("admin"),
    MANAGER("manager"),
    CLIENT("client");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
