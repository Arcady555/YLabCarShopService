package ru.parfenov.homework_3.servlets.manager;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.service.OrderService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@WebServlet(name = "AllOrdersServlet", urlPatterns = "/all-orders")
public class AllOrdersServlet extends HttpServlet {
    private final OrderService orderService = Utility.loadOrderService();

    public AllOrdersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Order> orderList = orderService.findAll();
        String orderJsonString = !orderList.isEmpty() ? new Gson().toJson(orderList) : "no orders!";
        response.setStatus("no orders!".equals(orderJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }
}
