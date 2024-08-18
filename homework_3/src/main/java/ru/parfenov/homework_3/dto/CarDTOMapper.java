package ru.parfenov.homework_3.dto;

import org.mapstruct.Mapping;
import ru.parfenov.homework_3.model.Car;

public interface CarDTOMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "yearOfProd", source = "yearOfProd")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "condition", source = "condition")
    CarDTO toCarDTO(Car source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "yearOfProd", source = "yearOfProd")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "condition", source = "condition")
    Car toCar(CarDTO destination);
}