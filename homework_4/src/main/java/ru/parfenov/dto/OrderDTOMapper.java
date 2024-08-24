package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Order;

@Mapper
public interface OrderDTOMapper {
    Order toOrder(OrderDTO destination);
}