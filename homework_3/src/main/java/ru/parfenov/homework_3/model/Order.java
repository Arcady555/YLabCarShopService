package ru.parfenov.homework_3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.homework_3.enums.OrderStatus;
import ru.parfenov.homework_3.enums.OrderType;

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
    private int authorId;
    private int carId;
    private OrderType type;
    private OrderStatus status;

    @Override
    public String toString() {
        return "id: " + getId() + ", " +
                "authorId: " + getAuthorId() + ", " +
                "car id: " + getCarId() + ", " +
                "type: " + getType() + ", " +
                "status: " + getStatus() + ".";
    }
}