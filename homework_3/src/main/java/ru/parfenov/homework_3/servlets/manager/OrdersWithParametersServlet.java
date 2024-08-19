package ru.parfenov.homework_3.servlets.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.Order;
import ru.parfenov.homework_3.model.User;
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
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String orderJsonString;
        if (user == null || user.getRole() != UserRole.MANAGER) {
            orderJsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            String authorIdStr = request.getParameter("authorId");
            String carIdStr = request.getParameter("carId");
            String typeStr = request.getParameter("type");
            String statusStr = request.getParameter("status");
            List<Order> orderList = orderService.findByParameter(authorIdStr, carIdStr, typeStr, statusStr);
            orderJsonString = !orderList.isEmpty() ? objectMapper.writeValueAsString(orderList) : "no orders with these parameters";
            responseStatus = "no orders with these parameters".equals(orderJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }
}