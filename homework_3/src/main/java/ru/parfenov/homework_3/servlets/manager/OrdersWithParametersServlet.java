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
@WebServlet(name = "OrdersWithParametersServlet", urlPatterns = "/orders-with-parameters")
public class OrdersWithParametersServlet extends HttpServlet {
    private final OrderService orderService = Utility.loadOrderService();

    public OrdersWithParametersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorIdStr = request.getParameter("authorId");
        String carIdStr = request.getParameter("carId");
        String typeStr = request.getParameter("type");
        String statusStr = request.getParameter("status");
        List<Order> orderList = orderService.findByParameter(authorIdStr, carIdStr, typeStr, statusStr);
        String orderJsonString = !orderList.isEmpty() ? new Gson().toJson(orderList) : "no orders with these parameters";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }
}
