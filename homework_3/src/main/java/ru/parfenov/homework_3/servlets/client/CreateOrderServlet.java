package ru.parfenov.homework_3.servlets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.OrderDTO;
import ru.parfenov.homework_3.enums.OrderType;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.service.OrderService;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@WebServlet(name = "CreateOrderServlet", urlPatterns = "/create-order")
public class CreateOrderServlet extends HttpServlet {
    private final OrderService orderService;
    private final CarService carService;
    private final UserService userService;

    public CreateOrderServlet() throws Exception {
        carService = Utility.loadCarService();
        orderService = Utility.loadOrderService();
        userService = Utility.loadUserservice();
    }

    public CreateOrderServlet(OrderService orderService, CarService carService, UserService userService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute("user");
        int responseStatus = user == null ? 401 : 403;
        String orderJsonString = "no rights or registration!";
        if (user != null && (user.getRole() != null)) {
            Scanner scanner = new Scanner(request.getInputStream());
            String orderJson = scanner.useDelimiter("\\A").next();
            scanner.close();
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDTO orderDTO = objectMapper.readValue(orderJson, OrderDTO.class);
            if (checkCorrelation(user, orderDTO) ) {
                Optional<Order> orderOptional = orderService.create(
                        orderDTO.getAuthorId(),
                        orderDTO.getCarId(),
                        orderDTO.getType());
                orderOptional.ifPresent(order -> buysAmountPlus(order, user));
                orderJsonString = orderOptional.isPresent() ?
                        objectMapper.writeValueAsString(orderOptional.get()) :
                        "order is not created!";
                responseStatus = "order is not created!".equals(orderJsonString) ? 404 : 200;
            }
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }

    private boolean checkCorrelation(User user, OrderDTO order) {
        boolean firstCheck = carService.isOwnCar(user.getId(), order.getCarId()) &&
                order.getType().equals("SERVICE");
        boolean secondCheck = !carService.isOwnCar(user.getId(), order.getCarId())
                        && order.getType().equals("BUY");
        return firstCheck || secondCheck;
    }

    private void buysAmountPlus(Order order, User user) {
        if (order.getType() == OrderType.BUY) {
            int buysAmount = user.getBuysAmount();
            buysAmount++;
            userService.update(
                    user.getId(), "", "", "", "", buysAmount
            );
        }
    }
}