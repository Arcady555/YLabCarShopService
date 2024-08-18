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
 * Страница позволяет провести поиск по нужным параметрам, можно указывать не все
 */
@Slf4j
@WebServlet(name = "CarsWithParametersServlet", urlPatterns = "/cars-with-parameters")
public class CarsWithParametersServlet extends HttpServlet {
    private final CarService carService = Utility.loadCarService();

    public CarsWithParametersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ownerId = request.getParameter("ownerId");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String yearOfProd = request.getParameter("yearOfProd");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");
        String condition = request.getParameter("condition");
        List<Car> carList = carService.findByParameter(
                ownerId, brand, model, yearOfProd, priceFrom, priceTo, condition
        );
        String carJsonString = !carList.isEmpty() ? new Gson().toJson(carList) : "no cars!";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(carJsonString);
        out.flush();
    }
}