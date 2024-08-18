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
import java.util.Optional;

@Slf4j
@WebServlet(name = "ViewOrderServlet", urlPatterns = "/order")
public class ViewOrderServlet extends HttpServlet {
    private final OrderService orderService = Utility.loadOrderService();

    public ViewOrderServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderIdStr = request.getParameter("id");
        Optional<Order> orderOptional = orderService.findById(orderIdStr);
        String orderJsonString = orderOptional.isPresent() ? new Gson().toJson(orderOptional.get()) : "order not found!";
        response.setStatus("order not found!".equals(orderJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setStatus(orderOptional.isPresent() ? 200 : 404);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }
}