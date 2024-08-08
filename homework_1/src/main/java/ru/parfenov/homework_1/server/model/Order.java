package ru.parfenov.homework_1.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    private int id;
    private User author;
    private int carId;
    private OrderType type;
    private OrderStatus status;
}