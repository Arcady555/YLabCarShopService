package ru.parfenov.enums;

public enum PersonRole {
    ADMIN("admin"),
    MANAGER("manager"),
    CLIENT("client");

    private final String name;

    PersonRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
