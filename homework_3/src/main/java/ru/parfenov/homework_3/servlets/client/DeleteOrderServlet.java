package ru.parfenov.homework_3.servlets.client;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.OrderService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(name = "DeleteOrderServlet", urlPatterns = "/delete-order")
public class DeleteOrderServlet extends HttpServlet {
    private final OrderService orderService;

    public DeleteOrderServlet() throws Exception {
        orderService = Utility.loadOrderService();
    }

    public DeleteOrderServlet(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String jsonString;
        if (user == null) {
            jsonString = "no registration!";
            responseStatus = 401;
        } else {
            String orderIdStr = request.getParameter("id");
            jsonString = orderService.delete(orderIdStr) ? "order is deleted" : "order is not deleted!";
            responseStatus = "order is not deleted!".equals(jsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}
