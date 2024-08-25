package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.parfenov.dto.CarDTO;
import ru.parfenov.dto.CarDTOMapper;
import ru.parfenov.model.Car;
import ru.parfenov.service.CarService;

import java.util.ArrayList;
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

    @GetMapping("/{carId}")
    public ResponseEntity<CarDTO> viewCar(@PathVariable int carId) {
        Optional<Car> carOptional = carService.findById(carId);
        return carOptional
                .map(car -> new ResponseEntity<>(dtoMapper.toCarDTO(car), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/update")
    public ResponseEntity<CarDTO> update(@RequestBody CarDTO carDTO) {
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

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<String> delete(@PathVariable int carId) {
        boolean isCarDeleted = carService.delete(carId);
        return isCarDeleted ?
                new ResponseEntity<>("Car is deleted", HttpStatus.OK) :
                new ResponseEntity<>("Car is not deleted", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("create")
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
            List<CarDTO> carListDTO = new ArrayList<>();
            for (Car car : carList) {
                carListDTO.add(dtoMapper.toCarDTO(car));
            }
            return new ResponseEntity<>(carListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<CarDTO>> findAll() {
        List<Car> carList = carService.findAll();
        if (!carList.isEmpty()) {
            List<CarDTO> carListDTO = new ArrayList<>();
            for (Car car : carList) {
                carListDTO.add(dtoMapper.toCarDTO(car));
            }
            return new ResponseEntity<>(carListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
