package ru.parfenov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.parfenov.dto.OrderDTO;
import ru.parfenov.enums.OrderStatus;
import ru.parfenov.enums.OrderType;
import ru.parfenov.enums.Role;
import ru.parfenov.model.Order;
import ru.parfenov.model.Person;
import ru.parfenov.repository.OrderRepository;
import ru.parfenov.service.CarService;
import ru.parfenov.service.OrderService;
import ru.parfenov.service.PersonService;

import java.util.List;
import java.util.Optional;

import static ru.parfenov.utility.Utility.getIntFromString;

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

    @Override
    public Optional<Order> create(OrderDTO orderDTO) {
        Optional<Order> orderOptional = Optional.empty();
        if (checkCorrelationForCreate(orderDTO)) {
            OrderType type = getOrderTypeFromString(orderDTO.getType());
            orderOptional = Optional.of(repo.save(
                    new Order(0, orderDTO.getAuthorId(), orderDTO.getCarId(), type, OrderStatus.OPEN)
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
        int id = getIntFromString(orderId);
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
        order.ifPresent(repo::save);
        return repo.findById(orderId).get().getStatus().equals(OrderStatus.CLOSED);
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
        int authorId = getIntFromString(authorIdStr);
        int carId = getIntFromString(carIdStr);
        OrderType type = getOrderTypeFromString(typeStr);
        OrderStatus status = "open".equals(statusStr) ?
                OrderStatus.OPEN :
                ("closed".equals(statusStr) ? OrderStatus.CLOSED : null);
        return repo.findByParameter(authorId, carId, type, status);
    }

    private OrderType getOrderTypeFromString(String str) {
        return "buy".equals(str) ?
                OrderType.BUY :
                ("service".equals(str) ? OrderType.SERVICE : null);
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

    private int getPersonId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String personIdStr = authentication.getName();
        return getIntFromString(personIdStr);
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