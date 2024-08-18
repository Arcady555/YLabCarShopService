package ru.parfenov.homework_3.servlets.manager;

import com.google.gson.Gson;
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
@WebServlet(name = "AllOrdersServlet", urlPatterns = "/all-orders")
public class AllOrdersServlet extends HttpServlet {
    private final OrderService orderService = Utility.loadOrderService();

    public AllOrdersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String orderListJsonString;
        if (user == null || user.getRole() != UserRole.ADMIN) {
            orderListJsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            List<Order> orderList = orderService.findAll();
            orderListJsonString = !orderList.isEmpty() ? new Gson().toJson(orderList) : "no orders!";
            responseStatus = "no orders!".equals(orderListJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderListJsonString);
        out.flush();
    }
}
