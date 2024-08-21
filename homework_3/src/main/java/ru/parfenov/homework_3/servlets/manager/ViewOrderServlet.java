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
import java.util.Optional;

@Slf4j
@WebServlet(name = "ViewOrderServlet", urlPatterns = "/order")
public class ViewOrderServlet extends HttpServlet {
    private final OrderService orderService;

    public ViewOrderServlet() throws Exception {
        orderService = Utility.loadOrderService();
    }

    public ViewOrderServlet(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute("user");
        int responseStatus = user == null ? 401 : 403;
        String orderJsonString = "no rights or registration!";
        if (user != null && (user.getRole() == UserRole.MANAGER || user.getRole() == UserRole.ADMIN)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String orderIdStr = request.getParameter("id");
            Optional<Order> orderOptional = orderService.findById(orderIdStr);
            orderJsonString = orderOptional.isPresent() ? objectMapper.writeValueAsString(orderOptional.get()) : "order not found!";
            responseStatus = "order not found!".equals(orderJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(orderJsonString);
        out.flush();
    }
}