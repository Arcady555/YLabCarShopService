package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.parfenov.dto.OrderDTO;
import ru.parfenov.dto.OrderDTOMapper;
import ru.parfenov.model.Order;
import ru.parfenov.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderDTOMapper dtoMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderDTOMapper dtoMapper) {
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable int orderId) {
        Optional<Order> orderOptional = orderService.findById(orderId);
        return orderOptional
                .map(order -> new ResponseEntity<>(dtoMapper.toOrderDTO(order), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("create")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        Optional<Order> orderOptional = orderService.create(
                orderDTO.getAuthorId(),
                orderDTO.getCarId(),
                orderDTO.getType()
        );
        return orderOptional
                .map(order -> new ResponseEntity<>(dtoMapper.toOrderDTO(order), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/close")
    public ResponseEntity<OrderDTO> close(@PathVariable int orderId) {
        boolean isOrderClosed = orderService.close(orderId);
        if (isOrderClosed) {
            Optional<Order> orderOptional = orderService.findById(orderId);
            return orderOptional
                    .map(order -> new ResponseEntity<>(dtoMapper.toOrderDTO(order), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> delete(@PathVariable int orderId) {
        boolean isOrderDeleted = orderService.delete(orderId);
        return isOrderDeleted ?
                new ResponseEntity<>("Order is deleted", HttpStatus.OK) :
                new ResponseEntity<>("Order is not deleted", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find-by_parameters")
    public ResponseEntity<List<OrderDTO>> findByParam(
            @RequestParam String authorId,
            @RequestParam String carId,
            @RequestParam String type,
            @RequestParam String status
    ) {
        List<Order> orderList = orderService.findByParameter(authorId, carId, type, status);
        if (!orderList.isEmpty()) {
            List<OrderDTO> orderListDTO = new ArrayList<>();
            for (Order order : orderList) {
                orderListDTO.add(dtoMapper.toOrderDTO(order));
            }
            return new ResponseEntity<>(orderListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<Order> orderList = orderService.findAll();
        if (!orderList.isEmpty()) {
            List<OrderDTO> orderListDTO = new ArrayList<>();
            for (Order order : orderList) {
                orderListDTO.add(dtoMapper.toOrderDTO(order));
            }
            return new ResponseEntity<>(orderListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}