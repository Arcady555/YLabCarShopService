package ru.parfenov.homework_1.server.model;

import ru.parfenov.homework_1.server.enums.UserRoles;

public class User {
    private final int id;
    private UserRoles role = UserRoles.CLIENT;
    private String name = "no name";
    private String password;
    private String contactInfo = "no data";
    private int buysAmount = 0;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getBuysAmount() {
        return buysAmount;
    }

    public void setBuysAmount(int buysAmount) {
        this.buysAmount = buysAmount;
    }
}