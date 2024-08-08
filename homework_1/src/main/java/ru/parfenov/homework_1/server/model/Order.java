package ru.parfenov.homework_1.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.homework_1.server.enums.OrderStatus;
import ru.parfenov.homework_1.server.enums.OrderType;

/**
 * Модель заказа
 * У заказа есть его создатель, id машины(на которую создали заказ), тип(продажа или сервис), статус(открыт или закрыт)
 */

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

    @Override
    public String toString() {
        return "id: " + getId() + ", " +
                "author: " + getAuthor() + ", " +
                "car id: " + getCarId() + ", " +
                "type: " + getType() + ", " +
                "status: " + getStatus() + ".";
    }
}