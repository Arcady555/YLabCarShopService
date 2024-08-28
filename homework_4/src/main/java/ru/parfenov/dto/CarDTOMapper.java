package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Car;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarDTOMapper {
    /**
     * Перевод сущности в DTO
     * (все поля становятся или int или String)
     *
     * @param source Car - сущность из блока ru/parfenov/homework_3/model
     * @return DTO объект
     */
    CarDTO toCarDTO(Car source);

    /**
     * Преобразование каждого элемента в списке по методу toCarDTO()
     *
     * @param source список сущностей Car под преобразование
     * @return список полученных элементов
     */
    List<CarDTO> toListCarDTO(List<Car> source);

}