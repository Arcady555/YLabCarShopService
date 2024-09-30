package ru.homework_5.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework_5.dto.OrderDTO;
import ru.homework_5.service.PersonService;
import ru.homework_5.utility.Utility;
import ru.homework_5.enums.OrderStatus;
import ru.homework_5.enums.OrderType;
import ru.homework_5.enums.Role;
import ru.homework_5.model.Order;
import ru.homework_5.model.Person;
import ru.homework_5.repository.OrderRepository;
import ru.homework_5.service.CarService;
import ru.homework_5.service.OrderService;

import java.util.List;
import java.util.Optional;

import static ru.homework_5.utility.Utility.getPersonId;

@Slf4j
@Service
public class OrderServiceSpringImpl implements OrderService {
    private final OrderRepository repo;
    private final PersonService personService;
    private final CarService carService;

    @Autowired
    public OrderServiceSpringImpl(OrderRepository repo, PersonService personService, CarService carService) {
        this.repo = repo;
        this.personService = personService;
        this.carService = carService;
    }

    /**
     * Заказ на покупку юзер может создать только если машина не его
     * А заказ на сервис - если машина его
     * После покупки машины его buysAmount++
     */
    @Override
    public Optional<Order> create(OrderDTO orderDTO) {
        Optional<Order> orderOptional = Optional.empty();
        int authorId = getPersonId();
        if (checkCorrelationForCreate(orderDTO)) {
            OrderType type = getOrderTypeFromString(orderDTO.getType());
            orderOptional = Optional.of(repo.save(
                    new Order(0, authorId, orderDTO.getCarId(), type, OrderStatus.OPEN)
            ));
        }
        if (orderOptional.isPresent()) {
            Optional<Person> personOptional = personService.findById(getPersonId());
            if (personOptional.isPresent()) {
                Order order = orderOptional.get();
                buysAmountPlus(order, personOptional.get());
            }
        }
        return orderOptional;
    }

    @Override
    public Optional<Order> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public boolean isOwnOrder(int authorId, String orderId) {
        int id = Utility.getIntFromString(orderId);
        return isOwnOrder(authorId, id);
    }

    @Override
    public boolean isOwnOrder(int authorId, int orderId) {
        boolean result = false;
        Optional<Order> orderOptional = findById(orderId);
        if (orderOptional.isPresent()) {
            result = orderOptional.get().getAuthorId() == authorId;
        }
        return result;
    }

    @Override
    public boolean close(int orderId) {
        Optional<Order> order = findById(orderId);
        if (order.isPresent()) {
            order.get().setStatus(OrderStatus.CLOSED);
            repo.save(order.get());
        }
        return findById(orderId).isPresent() && findById(orderId).get().getStatus().equals(OrderStatus.CLOSED);
    }

    @Override
    public boolean delete(int id) {
        if (checkCorrelationForDelete(id)) {
            Optional<Order> order = findById(id);
            order.ifPresent(repo::delete);
        }
        return !repo.existsById(id);
    }

    @Override
    public List<Order> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Order> findByAuthorId(int authorId) {
        return repo.findByAuthorId(authorId);
    }

    @Override
    public List<Order> findByParameter(String authorIdStr, String carIdStr, String typeStr, String statusStr) {
        int authorId = Utility.getIntFromString(authorIdStr);
        int carId = Utility.getIntFromString(carIdStr);
        OrderType type = getOrderTypeFromString(typeStr);
        OrderStatus status = "OPEN".equals(statusStr) ?
                OrderStatus.OPEN :
                ("CLOSED".equals(statusStr) ? OrderStatus.CLOSED : null);
        return repo.findByParameter(authorId, carId, type, status);
    }

    private OrderType getOrderTypeFromString(String str) {
        return "BUY".equals(str) ?
                OrderType.BUY :
                ("SERVICE".equals(str) ? OrderType.SERVICE : null);
    }

    private boolean checkCorrelationForDelete(int orderId) {
        int personId = getPersonId();
        Optional<Person> personOptional = personService.findById(personId);
        Person person = personOptional.orElse(null);
        boolean ownCheck = isOwnOrder(personId, orderId);
        boolean nullCheck = person != null;
        return ownCheck || (
                nullCheck &&
                        (person.getRole().equals(Role.ADMIN) || person.getRole().equals(Role.MANAGER))
        );
    }

    private boolean checkCorrelationForCreate(OrderDTO order) {
        int personId = getPersonId();
        boolean firstCheck = carService.isOwnCar(personId, order.getCarId()) &&
                order.getType().equals("SERVICE");
        boolean secondCheck = !carService.isOwnCar(personId, order.getCarId())
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