package ru.parfenov.enums;

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