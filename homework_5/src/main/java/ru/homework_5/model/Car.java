package ru.homework_5.model;

import jakarta.persistence.*;
import lombok.*;
import ru.homework_5.enums.CarCondition;

/**
 * Модель машины
 * у машины есть собственник, бренд(марка), модель, год выпуска, цена и состояние(новая или б/у)
 */

@Entity
@Table(name = "cars", schema = "cs_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "owner_id")
    private int ownerId;
    private String brand;
    private String model;
    @Column(name = "year_of_prod")
    private int yearOfProd;
    private int price;
    @Enumerated(EnumType.STRING)
    @Column(name = "car_condition")
    private CarCondition condition;

    @Override
    public String toString() {
        return "id: " + getId() + ", " +
                "ownerId: " + getOwnerId() + ", " +
                "brand: " + getBrand() + ", " +
                "model: " + getModel() + ", " +
                "year of produce: " + getYearOfProd() + ", " +
                "price: " + getPrice() + ", " +
                "condition: " + getCondition() + ".";
    }
}
