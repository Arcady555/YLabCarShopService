package ru.homework_5.dto;

import org.mapstruct.Mapper;
import ru.homework_5.model.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDTOMapper {

    /**
     * Перевод сущности в DTO
     * (все поля становятся или int или String)
     *
     * @param source Order - сущность из блока ru/parfenov/homework_3/model
     * @return DTO объект
     */
    OrderDTO toOrderDTO(Order source);

    /**
     * Преобразование каждого элемента в списке по методу toCarDTO()
     *
     * @param source список сущностей Car под преобразование
     * @return список полученных элементов
     */
    List<OrderDTO> toOrderListDTO(List<Order> source);
}