package ru.parfenov.homework_3.servlets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.CarDTO;
import ru.parfenov.homework_3.model.Car;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

/**
 * Страница, где пользователь может ввести машину в базу данных
 */
@Slf4j
@WebServlet(name = "CreateCarServlet", urlPatterns = "/create-car")
public class CreateCarServlet extends HttpServlet {
    private final CarService carService = Utility.loadCarService();

    public CreateCarServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        CarDTO carDTO = objectMapper.readValue(userJson, CarDTO.class);
        Optional<Car> carOptional = carService.create(
                carDTO.getOwnerId(),
                carDTO.getBrand(),
                carDTO.getModel(),
                carDTO.getYearOfProd(),
                carDTO.getPrice(),
                carDTO.getCondition()
        );
        String carJsonString = carOptional.isPresent() ?
                new Gson().toJson(carOptional.get()) :
                "car is not created!";
        response.setStatus("car is not created!".equals(carJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(carJsonString);
        out.flush();
    }
}