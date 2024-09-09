package ru.homework_5.dto;

import org.mapstruct.Mapper;
import ru.homework_5.model.Car;

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
     * Перевод сущности в DTO
     * (все поля становятся или int или String)
     *
     * @param destination CarDTO - сущность из блока ru/parfenov/homework_3/model, обёрнутая в DTO
     * @return DTO объект
     */
    Car toCar(CarDTO destination);

    /**
     * Преобразование каждого элемента в списке по методу toCarDTO()
     *
     * @param source список сущностей Car под преобразование
     * @return список полученных элементов
     */
    List<CarDTO> toListCarDTO(List<Car> source);

}