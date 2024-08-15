package ru.parfenov.homework_3.enums;

public enum OrderType {
    BUY("buy"),
    SERVICE("service");

    private final String name;

    OrderType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}