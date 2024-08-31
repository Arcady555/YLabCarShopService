package ru.parfenov.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.parfenov.dto.CarDTO;
import ru.parfenov.dto.CarDTOMapper;
import ru.parfenov.model.Car;
import ru.parfenov.service.CarService;
import ru.parfenov.service.PersonService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final PersonService personService;
    private final CarDTOMapper dtoMapper;

    @Autowired
    public CarController(CarService carService, PersonService personService, CarDTOMapper dtoMapper) {
        this.carService = carService;
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Вывод карточки машины по её ID
     *
     * @param carId ID машины
     * @return ответ сервера
     */
    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDTO> viewCar(@PathVariable int carId) {
        Optional<Car> carOptional = carService.findById(carId);
        return carOptional
                .map(car -> new ResponseEntity<>(dtoMapper.toCarDTO(car), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Страница обновления данных по машине
     * Метод обработает HTTP запрос Post.
     * Если юзер не админ и не менеджер, то он может обновить только свою машину
     *
     * @param request запрос на сервер от юзера
     * @param carDTO  сущность Car, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/update")
    public ResponseEntity<CarDTO> update(HttpServletRequest request, @RequestBody CarDTO carDTO) {
        boolean isCarUpdated = carService.update(
                carDTO.getId(),
                carDTO.getOwnerId(),
                carDTO.getBrand(),
                carDTO.getModel(),
                carDTO.getYearOfProd(),
                carDTO.getPrice(),
                carDTO.getCondition()
        );
        if (isCarUpdated) {
            Optional<Car> carOptional = carService.findById(carDTO.getId());
            return carOptional
                    .map(car -> new ResponseEntity<>(dtoMapper.toCarDTO(car), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Страница удаления данных по машине
     * Метод обработает HTTP запрос Delete.
     * Если юзер не админ и не менеджер, то он может удалить только свою машину
     *
     * @param request запрос на сервер от юзера
     * @param carId   ID машины
     * @return ответ сервера
     */
    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<String> delete(HttpServletRequest request, @PathVariable int carId) {
        boolean isCarDeleted = carService.delete(carId);
        return isCarDeleted ?
                new ResponseEntity<>("Car is deleted", HttpStatus.OK) :
                new ResponseEntity<>("Car is not deleted", HttpStatus.BAD_REQUEST);
    }

    /**
     * Страница, где пользователь может ввести машину в базу данных
     * Метод обработает HTTP запрос Post.
     *
     * @param carDTO сущность Car, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/create")
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO) {
        Optional<Car> carOptional = carService.create(
                carDTO.getOwnerId(),
                carDTO.getBrand(),
                carDTO.getModel(),
                carDTO.getYearOfProd(),
                carDTO.getPrice(),
                carDTO.getCondition()
        );
        return carOptional
                .map(car -> new ResponseEntity<>(dtoMapper.toCarDTO(car), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Страница позволяет провести поиск по нужным параметрам, можно указывать не все
     *
     * @param ownerId    ID собственника
     * @param brand      марка машины
     * @param model      модель машины
     * @param yearOfProd год выпуска
     * @param priceFrom  цена от
     * @param priceTo    цена до
     * @param condition  состояние
     * @return ответ сервера
     */
    @GetMapping("/find-by_parameters")
    public ResponseEntity<List<CarDTO>> findByParam(
            @RequestParam String ownerId,
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String yearOfProd,
            @RequestParam String priceFrom,
            @RequestParam String priceTo,
            @RequestParam String condition
    ) {
        List<Car> carList = carService.findByParameter(ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition);
        if (!carList.isEmpty()) {
            List<CarDTO> carListDTO = dtoMapper.toListCarDTO(carList);
            return new ResponseEntity<>(carListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Страница вывода списка всех машин
     * Метод обработает HTTP запрос Get.
     *
     * @return ответ сервера
     */
    @GetMapping("/all")
    public ResponseEntity<List<CarDTO>> findAll() {
        List<Car> carList = carService.findAll();
        if (!carList.isEmpty()) {
            List<CarDTO> carListDTO = dtoMapper.toListCarDTO(carList);
            return new ResponseEntity<>(carListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}