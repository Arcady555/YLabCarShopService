package ru.parfenov.homework_3.servlets.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.OrderService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(name = "CloseOrderServlet", urlPatterns = "/close-order")
public class CloseOrderServlet extends HttpServlet {
    private final OrderService orderService = Utility.loadOrderService();

    public CloseOrderServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String jsonString;
        if (user == null || user.getRole() != UserRole.MANAGER) {
            jsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            String orderIdStr = request.getParameter("id");
            orderService.close(orderIdStr);
            jsonString = orderService.close(orderIdStr) ? "order is closed" : "order is not closed!";
            responseStatus = "order is not closed!".equals(jsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}