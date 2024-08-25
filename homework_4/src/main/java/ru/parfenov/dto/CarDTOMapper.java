package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Car;

@Mapper(componentModel = "spring")
public interface CarDTOMapper {
    CarDTO toCarDTO(Car source);

    Car toCar(CarDTO destination);
}