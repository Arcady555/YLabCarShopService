package ru.parfenov.homework_3.enums;

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