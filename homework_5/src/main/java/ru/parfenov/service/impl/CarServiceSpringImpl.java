package ru.parfenov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.parfenov.dto.CarDTO;
import ru.parfenov.enums.CarCondition;
import ru.parfenov.enums.Role;
import ru.parfenov.model.Car;
import ru.parfenov.model.Person;
import ru.parfenov.repository.CarRepository;
import ru.parfenov.service.CarService;
import ru.parfenov.service.PersonService;

import java.util.List;
import java.util.Optional;

import static ru.parfenov.utility.Utility.getIntFromString;

@Slf4j
@Service
public class CarServiceSpringImpl implements CarService {
    private final CarRepository repo;
    private final PersonService personService;

    @Autowired
    public CarServiceSpringImpl(CarRepository repo, PersonService personService) {
        this.repo = repo;
        this.personService = personService;
    }

    @Override
    public Optional<Car> create(int ownerId, String brand, String model, int yearOfProd, int price, String conditionStr) {
        CarCondition condition = getCarConditionFromString(conditionStr);
        return Optional.of(repo.save(new Car(0, ownerId, brand, model, yearOfProd, price, condition)));
    }

    @Override
    public Optional<Car> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public List<Car> findByOwner(int ownerId) {
        return repo.findByOwnerId(ownerId);
    }

    @Override
    public boolean isOwnCar(int ownerId, int carId) {
        boolean result = false;
        Optional<Car> carOptional = findById(carId);
        if (carOptional.isPresent()) {
            result = carOptional.get().getOwnerId() == ownerId;
        }
        return result;
    }

    @Override
    public boolean isOwnCar(int ownerId, String carId) {
        int id = getIntFromString(carId);
        return isOwnCar(ownerId, id);
    }

    @Override
    public boolean update(CarDTO carDTO) {
        if (checkCorrelation(carDTO.getId())) {
            CarCondition condition = getCarConditionFromString(carDTO.getCondition());
            Car car = new Car(carDTO.getId(),
                    carDTO.getOwnerId(),
                    carDTO.getBrand(),
                    carDTO.getModel(),
                    carDTO.getYearOfProd(),
                    carDTO.getPrice(),
                    condition
            );
            repo.save(car);
        }
        return repo.existsById(carDTO.getId());
    }

    @Override
    public boolean delete(int id) {
        if (checkCorrelation(id)) {
            Optional<Car> car = findById(id);
            car.ifPresent(repo::delete);
        }
        return !repo.existsById(id);
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
        return repo.findByParameters(ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition);
    }

    private CarCondition getCarConditionFromString(String str) {
        return "new".equals(str) ?
                CarCondition.NEW :
                ("used".equals(str) ? CarCondition.NEW : null);
    }

    private boolean checkCorrelation(int carId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String personIdStr = authentication.getName();
        int personId = getIntFromString(personIdStr);
        Optional<Person> personOptional = personService.findById(personId);
        Person person = personOptional.orElse(null);
        boolean ownCheck = isOwnCar(personId, carId);
        boolean nullCheck = person != null;
        return ownCheck || (
                nullCheck &&
                        (person.getRole().equals(Role.ADMIN) || person.getRole().equals(Role.MANAGER))
        );
    }
}