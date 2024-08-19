package ru.parfenov.homework_3.servlets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Вывод карточки машины по её ID
 */
@Slf4j
@WebServlet(name = "ViewCarServlet", urlPatterns = "/car")
public class ViewCarServlet extends HttpServlet {
    private final CarService carService = Utility.loadCarService();

    public ViewCarServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String carJsonString;
        if (user == null) {
            carJsonString = "no registration!";
            responseStatus = 401;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            String carIdStr = request.getParameter("id");
            Optional<Car> carOptional = carService.findById(carIdStr);
            carJsonString = carOptional.isPresent() ? objectMapper.writeValueAsString(carOptional.get()) : "car not found!";
            responseStatus = "car not found!".equals(carJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(carJsonString);
        out.flush();
    }
}