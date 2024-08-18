package ru.parfenov.homework_3.servlets.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
        String orderIdStr = request.getParameter("id");
        orderService.close(orderIdStr);
        String jsonString = orderService.close(orderIdStr) ? "order is closed" : "order is not closed!";
        response.setStatus("order is not closed!".equals(jsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}