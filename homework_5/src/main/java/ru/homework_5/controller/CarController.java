package ru.homework_5.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework_5.dto.CarDTO;
import ru.homework_5.dto.CarDTOMapper;
import ru.homework_5.model.Car;
import ru.homework_5.service.CarService;
import ru.parfenov.anotation.EnableParfenovCustomAspect;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarDTOMapper dtoMapper;

    @Autowired
    public CarController(CarService carService, CarDTOMapper dtoMapper) {
        this.carService = carService;
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
     * @param carDTO сущность Car, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/update")
    @EnableParfenovCustomAspect
    public ResponseEntity<CarDTO> update(@RequestBody CarDTO carDTO) {
        boolean isCarUpdated = carService.update(carDTO);
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
     * @param carId ID машины
     * @return ответ сервера
     */
    @DeleteMapping("/delete/{carId}")
    @EnableParfenovCustomAspect
    public ResponseEntity<String> delete(@PathVariable int carId) {
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
    @EnableParfenovCustomAspect
    @PostMapping("/create")
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO) {
        Optional<Car> carOptional = carService.create(
              //  carDTO.getOwnerId(),
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
     * Параметры указываются в адресной строке запроса("/find-by_parameters?ownerId=2&&brand=.... и тд)
     * поиск можно проводить не по всем 6м параметрам. Некоторые можно не указывать после "=",
     * тогда они в отборе не участвуют (для int параметров ownerId, yearOfProd, priceFrom, priceTo в этом случае
     * надо указать -1 ("yearOfProd=-1", например)).
     * Данный метод доступен только админу(через SecurityConfig)
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