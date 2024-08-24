package ru.parfenov.homework_3.enums;

public enum OrderStatus {
    OPEN("open"),
    CLOSED("closed");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}