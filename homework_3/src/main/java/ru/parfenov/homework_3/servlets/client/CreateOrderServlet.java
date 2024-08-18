package ru.parfenov.homework_3.servlets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.OrderDTO;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.service.OrderService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@WebServlet(name = "CreateOrderServlet", urlPatterns = "/create-order")
public class CreateOrderServlet extends HttpServlet {
    private final OrderService orderService = Utility.loadOrderService();

    public CreateOrderServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDTO orderDTO = objectMapper.readValue(userJson, OrderDTO.class);
        Optional<Order> orderOptional = orderService.create(
                orderDTO.getAuthorId(),
                orderDTO.getCarId(),
                orderDTO.getType()
        );
        String orderJsonString = orderOptional.isPresent() ?
                new Gson().toJson(orderOptional.get()) :
                "order is not created!";
        response.setStatus("order is not created!".equals(orderJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }
}