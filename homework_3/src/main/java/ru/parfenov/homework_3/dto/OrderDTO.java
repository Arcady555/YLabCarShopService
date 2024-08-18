package ru.parfenov.homework_3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private int id;
    private int authorId;
    private int carId;
    private String type;
    private String status;
}