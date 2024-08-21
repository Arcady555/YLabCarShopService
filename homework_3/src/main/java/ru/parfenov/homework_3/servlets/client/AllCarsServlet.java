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
import java.util.List;

/**
 * Страница вывода списка всех машин
 */
@Slf4j
@WebServlet(name = "AllCarsServlet", urlPatterns = "/all-cars")
public class AllCarsServlet extends HttpServlet {
    private final CarService carService;

    public AllCarsServlet() throws Exception {
        carService = Utility.loadCarService();
    }

    public AllCarsServlet(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String carListJsonString;
        if (user == null) {
            carListJsonString = "no registration!";
            responseStatus = 401;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Car> carList = carService.findAll();
            carListJsonString = !carList.isEmpty() ? objectMapper.writeValueAsString(carList) : "no cars!";
            responseStatus = "no cars!".equals(carListJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(carListJsonString);
        out.flush();
    }
}