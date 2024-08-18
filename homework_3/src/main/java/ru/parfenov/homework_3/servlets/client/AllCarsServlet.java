package ru.parfenov.homework_3.servlets.client;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.Car;
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
    private final CarService carService = Utility.loadCarService();

    public AllCarsServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Car> carList = carService.findAll();
        String carListJsonString = !carList.isEmpty() ? new Gson().toJson(carList) : "no cars!";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(carListJsonString);
        out.flush();
    }
}