package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.parfenov.dto.OrderDTO;
import ru.parfenov.dto.OrderDTOMapper;
import ru.parfenov.enums.OrderType;
import ru.parfenov.enums.PersonRole;
import ru.parfenov.model.Order;
import ru.parfenov.model.Person;
import ru.parfenov.service.CarService;
import ru.parfenov.service.OrderService;
import ru.parfenov.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static ru.parfenov.utility.Utility.getUserId;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final PersonService personService;
    private final CarService carService;
    private final OrderDTOMapper dtoMapper;

    @Autowired
    public OrderController(
            OrderService orderService,
            PersonService personService,
            CarService carService,
            OrderDTOMapper dtoMapper
    ) {
        this.orderService = orderService;
        this.personService = personService;
        this.carService = carService;
        this.dtoMapper = dtoMapper;
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
     * Страница создания заказа на покупку или сервис машины
     * Метод обработает HTTP запрос Post.
     * Заказ на покупку юзер может создать только если машина не его
     * А заказ на сервис - если машина его
     * После покупки машины его buysAmount++
     *
     * @param orderDTO ID заказа
     * @return ответ сервера
     */
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(HttpServletRequest request, @RequestBody OrderDTO orderDTO) {
        Optional<Order> orderOptional = Optional.empty();
        if (checkCorrelationForCreate(request, orderDTO)) {
            orderOptional = orderService.create(
                    orderDTO.getAuthorId(),
                    orderDTO.getCarId(),
                    orderDTO.getType());
        }
        if (orderOptional.isPresent()) {
            Optional<Person> userOptional = personService.findById(getUserId(request));
            if (userOptional.isPresent()) {
                Order order = orderOptional.get();
                buysAmountPlus(order, userOptional.get());
            }
            return new ResponseEntity<>(dtoMapper.toOrderDTO(orderOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<String> delete(HttpServletRequest request, @PathVariable int orderId) {
        boolean isOrderDeleted = false;
        if (checkCorrelationForDelete(request, orderId)) {
            isOrderDeleted = orderService.delete(orderId);
        }
        return isOrderDeleted ?
                new ResponseEntity<>("Order is deleted", HttpStatus.OK) :
                new ResponseEntity<>("Order is not deleted", HttpStatus.BAD_REQUEST);
    }

    /**
     * Страница вывода списка заказов по параметрам
     * Данный метод, доступный только менеджеру или админу(через фильтр сервлетов),
     * Метод обработает HTTP запрос Get.
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

    private boolean checkCorrelationForDelete(HttpServletRequest request, int orderId) {
        int userId = getUserId(request);
        Optional<Person> userOptional = personService.findById(userId);
        Person person = userOptional.orElse(null);
        boolean ownCheck = orderService.isOwnOrder(userId, orderId);
        boolean nullCheck = person != null;
        return ownCheck || (
                nullCheck &&
                        (person.getRole().equals(PersonRole.ADMIN) || person.getRole().equals(PersonRole.MANAGER))
        );
    }

    private boolean checkCorrelationForCreate(HttpServletRequest request, OrderDTO order) {
        int userId = getUserId(request);
        boolean firstCheck = carService.isOwnCar(userId, order.getCarId()) &&
                order.getType().equals("SERVICE");
        boolean secondCheck = !carService.isOwnCar(userId, order.getCarId())
                && order.getType().equals("BUY");
        return firstCheck || secondCheck;
    }

    private void buysAmountPlus(Order order, Person person) {
        if (order.getType() == OrderType.BUY) {
            int buysAmount = person.getBuysAmount();
            buysAmount++;
            personService.update(
                    person.getId(), "", "", "", "", buysAmount
            );
        }
    }
}