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
import ru.parfenov.homework_3.servlets.MethodsForServlets;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Страница вывода списка заказов по параметрам
 */
@Slf4j
@WebServlet(name = "OrdersWithParametersServlet", urlPatterns = "/orders-by-parameters")
public class OrdersWithParametersServlet extends HttpServlet implements MethodsForServlets {
    private final OrderService orderService;

    public OrdersWithParametersServlet() throws Exception {
        orderService = Utility.loadOrderService();
    }

    public OrdersWithParametersServlet(OrderService orderService) {
        this.orderService = orderService;
    }
    /**
     * Метод обработает HTTP запрос Get.
     * Есть проверки:
     *     что юзер открыл сессию,
     *     что зарегистрирован,
     *     что он менеджер или админ
     * @param request запрос клиента
     * @param response ответ сервера
     * @throws IOException исключение при вводе-выводе
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute("user");
        int responseStatus = user == null ? 401 : 403;
        String orderJsonString = "no rights or registration!";
        if (user != null && (user.getRole() == UserRole.MANAGER || user.getRole() == UserRole.ADMIN)) {
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
        finish(response, orderJsonString);
    }
}