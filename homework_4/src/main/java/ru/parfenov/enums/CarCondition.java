package ru.parfenov.enums;

public enum CarCondition {
    NEW("new"),
    USED("used");

    private final String name;

    CarCondition(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}