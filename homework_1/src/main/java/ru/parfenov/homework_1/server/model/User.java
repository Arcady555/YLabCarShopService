package ru.parfenov.homework_1.server.model;

import ru.parfenov.homework_1.server.enums.UserRoles;

public class User {
    private int id;
    private UserRoles role = UserRoles.CLIENT;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, UserRoles role, String name, String password, String contactInfo, int buysAmount) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.password = password;
        this.contactInfo = contactInfo;
        this.buysAmount = buysAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo;
    }
}