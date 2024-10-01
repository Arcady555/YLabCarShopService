package ru.homework_5.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.homework_5.enums.CarCondition;
import ru.homework_5.model.Car;
import ru.homework_5.repository.CarRepository;
import ru.homework_5.service.PersonService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CarServiceSpringImplTest {
    CarRepository repo = mock(CarRepository.class);
    PersonService personService = mock(PersonService.class);
    CarServiceSpringImpl carService = new CarServiceSpringImpl(repo, personService);

    @Test
    @WithMockUser
    @DisplayName("Create  с валидными данными")
    public void create_car_with_valid_data() {
        Car car = new Car(0, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(repo.save(any(Car.class))).thenReturn(car);

        Optional<Car> result = carService.create("Toyota", "Camry", 2020, 30000, "new");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(car, result.get());
    }

    @Test
    @DisplayName("Метод findById")
    public void find_car_by_id() {

        Car car = new Car(1, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW);
        when(repo.findById(1)).thenReturn(Optional.of(car));

        Optional<Car> result = carService.findById(1);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(car, result.get());
    }

    @Test
    @DisplayName("Метод findByOwnerId")
    public void find_cars_by_owner_id() {
        List<Car> cars = Arrays.asList(
                new Car(1, 1, "Toyota", "Camry", 2020, 30000, CarCondition.NEW),
                new Car(2, 1, "Honda", "Civic", 2019, 20000, CarCondition.USED)
        );
        when(repo.findByOwnerId(1)).thenReturn(cars);
        List<Car> result = carService.findByOwner(1);
        Assertions.assertEquals(cars.size(), result.size());
        Assertions.assertEquals(cars, result);
    }

    @Test
    @DisplayName("Поиск по несуществующему ID")
    public void find_car_by_non_existent_id() {
        when(repo.findById(999)).thenReturn(Optional.empty());
        Optional<Car> result = carService.findById(999);
        Assertions.assertFalse(result.isPresent());
    }
}