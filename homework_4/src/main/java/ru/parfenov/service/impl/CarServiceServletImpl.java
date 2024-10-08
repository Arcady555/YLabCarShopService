package ru.parfenov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.parfenov.enums.CarCondition;
import ru.parfenov.model.Car;
import ru.parfenov.repository.CarRepository;
import ru.parfenov.service.CarService;

import static ru.parfenov.utility.Utility.getIntFromString;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarServiceServletImpl implements CarService {
    private final CarRepository repo;

    @Autowired
    public CarServiceServletImpl(CarRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Car> create(int ownerId, String brand, String model, int yearOfProd, int price, String conditionStr) {
        CarCondition condition = getCarConditionFromString(conditionStr);
        return Optional.ofNullable(repo.create(new Car(0, ownerId, brand, model, yearOfProd, price, condition)));
    }

    @Override
    public Optional<Car> findById(int id) {
        return Optional.ofNullable(repo.findById(id));
    }

    @Override
    public List<Car> findByOwner(int ownerId) {
        return repo.findByOwner(ownerId);
    }

    @Override
    public boolean isOwnCar(int ownerId, int carId) {
        Car car = repo.findById(carId);
        return car != null && car.getId() == ownerId;
    }

    @Override
    public boolean isOwnCar(int ownerId, String carId) {
        int id = getIntFromString(carId);
        return isOwnCar(ownerId, id);
    }

    @Override
    public boolean update(int carId, int ownerId, String brand, String model, int yearOfProd, int price, String conditionStr) {
        CarCondition condition = getCarConditionFromString(conditionStr);
        Car car = new Car(carId, ownerId, brand, model, yearOfProd, price, condition);
        return repo.update(car);
    }

    @Override
    public boolean delete(int id) {
        return repo.delete(id);
    }

    @Override
    public List<Car> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Car> findByParameter(
            String ownerIdStr,
            String brand,
            String model,
            String yearOfProdStr,
            String priceFromStr,
            String priceToStr,
            String conditionStr
    ) {
        int ownerId = getIntFromString(ownerIdStr);
        int yearOfProd = getIntFromString(yearOfProdStr);
        int priceFrom = getIntFromString(priceFromStr);
        int priceTo = getIntFromString(priceToStr);
        CarCondition condition = getCarConditionFromString(conditionStr);
        return repo.findByParameter(ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition);
    }

    private CarCondition getCarConditionFromString(String str) {
        return "new".equals(str) ?
                CarCondition.NEW :
                ("used".equals(str) ? CarCondition.NEW : null);
    }
}