package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Car;

@Mapper
public interface CarDTOMapper {
    CarDTO toCarDTO(Car source);

    Car toCar(CarDTO destination);
}