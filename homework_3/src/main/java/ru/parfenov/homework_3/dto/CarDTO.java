package ru.parfenov.homework_3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {
    private int id;
    private int ownerId;
    private String brand;
    private String model;
    private int yearOfProd;
    private int price;
    private String condition;
}