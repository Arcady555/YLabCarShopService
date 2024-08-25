package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Order;

@Mapper(componentModel = "spring")
public interface OrderDTOMapper {
    Order toOrder(OrderDTO destination);

    OrderDTO toOrderDTO(Order source);
}