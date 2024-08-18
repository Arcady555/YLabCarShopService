package ru.parfenov.homework_3.dto;

import org.mapstruct.Mapping;
import ru.parfenov.homework_3.model.Order;

public interface OrderDTOMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "authorId", source = "authorId")
    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    OrderDTO toOrderDTO(Order source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "authorId", source = "authorId")
    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    Order toOrder(OrderDTO destination);
}