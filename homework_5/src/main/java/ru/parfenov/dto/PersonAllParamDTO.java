package ru.parfenov.dto;

import lombok.Data;
import org.mapstruct.Mapper;

/**
 * DTO для удобной подачи в json. Enum заменили на String
 */
@Data
public class PersonAllParamDTO {
    private int id;
    private String role;
    private String name;
    private String password;
    private String contactInfo;
    private int buysAmount;
}
