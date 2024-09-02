package ru.parfenov.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;

/**
 * Модель заказа
 * У заказа есть его создатель, id машины(на которую создали заказ), тип(продажа или сервис), статус(открыт или закрыт)
 */

@Entity
@Table(name = "orders", schema = "cs_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "author_id")
    private int authorId;
    @Column(name = "car_id")
    private int carId;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
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