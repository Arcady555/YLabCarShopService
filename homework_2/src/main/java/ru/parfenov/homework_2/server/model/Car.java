package ru.parfenov.homework_2.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.parfenov.homework_2.server.enums.CarCondition;

/**
 * Модель машины
 * у машины есть собственник, бренд(марка), модель, год выпуска, цена и состояние(новая или б/у)
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Car {
    private int id;
    private int ownerId;
    private String brand;
    private String model;
    private int yearOfProd;
    private int price;
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
