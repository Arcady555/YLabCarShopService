package ru.homework_5.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework_5.dto.OrderDTO;
import ru.homework_5.dto.OrderDTOMapper;
import ru.homework_5.model.Order;
import ru.homework_5.service.OrderService;
import ru.parfenov.anotation.EnableParfenovCustomAspect;

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

    /**
     * Страница создания заказа на покупку или сервис машины
     * Метод обработает HTTP запрос Post.
     * Заказ на покупку юзер может создать только если машина не его
     * А заказ на сервис - если машина его
     * После покупки машины его buysAmount++
     *
     * @param orderDTO сущность Order, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/create")
    @EnableParfenovCustomAspect
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        Optional<Order> orderOptional = orderService.create(orderDTO);
        return orderOptional.map(
                order -> new ResponseEntity<>(dtoMapper.toOrderDTO(order),
                        HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        );
    }

    /**
     * Страница просмотра карточки заказа
     * Метод обработает HTTP запрос Get.
     * Если юзер клиент, то может смотреть только свой заказ
     *
     * @param orderId ID заказа
     * @return ответ сервера
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable int orderId) {
        Optional<Order> orderOptional = orderService.findById(orderId);
        return orderOptional
                .map(order -> new ResponseEntity<>(dtoMapper.toOrderDTO(order), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Данный метод, доступный только менеджеру или админу(через фильтр сервлетов),
     * переводит заказа в статус "закрыт"
     * Метод обработает HTTP запрос Get.
     *
     * @param orderId ID заказа
     * @return ответ сервера
     */
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

    /**
     * Страница удаления заказа
     * Метод обработает HTTP запрос Delete.
     * Если юзер не админ и не менеджер, то он может удалить только свой заказ
     *
     * @param orderId ID заказа
     * @return ответ сервера
     */
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> delete(@PathVariable int orderId) {
        boolean isOrderDeleted = orderService.delete(orderId);
        return isOrderDeleted ?
                new ResponseEntity<>("Order is deleted", HttpStatus.OK) :
                new ResponseEntity<>("Order is not deleted", HttpStatus.BAD_REQUEST);
    }

    /**
     * Страница вывода списка всех заказов
     * Данный метод, доступный только менеджеру или админу(через фильтр сервлетов),
     * Метод обработает HTTP запрос Get.
     *
     * @return ответ сервера
     */
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<Order> orderList = orderService.findAll();
        if (!orderList.isEmpty()) {
            List<OrderDTO> orderListDTO = dtoMapper.toOrderListDTO(orderList);
            return new ResponseEntity<>(orderListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Страница вывода списка заказов по параметрам
     * Данный метод, доступный только менеджеру или админу(через SecurityConfig),
     * Метод обработает HTTP запрос Get.
     * Параметры указываются в адресной строке запроса("/find-by_parameters?authorId=2&&carId=.... и тд).
     * Поиск можно проводить не по всем 4м параметрам. Некоторые можно не указывать после "=",
     * тогда они в отборе не участвуют (для int параметров authorId и carId в этом случае
     * надо указать -1 ("carId=-1", например)).
     *
     * @param authorId ID создателя заказа
     * @param carId    ID машины
     * @param type     тип заказа
     * @param status   статус заказа
     * @return ответ сервера
     */
    @GetMapping("/find-by-parameters")
    public ResponseEntity<List<OrderDTO>> findByParam(
            @RequestParam String authorId,
            @RequestParam String carId,
            @RequestParam String type,
            @RequestParam String status
    ) {
        List<Order> orderList = orderService.findByParameter(authorId, carId, type, status);
        if (!orderList.isEmpty()) {
            List<OrderDTO> orderListDTO = dtoMapper.toOrderListDTO(orderList);
            return new ResponseEntity<>(orderListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}