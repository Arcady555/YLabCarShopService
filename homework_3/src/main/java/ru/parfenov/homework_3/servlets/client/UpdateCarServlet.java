package ru.parfenov.homework_3.servlets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.CarDTO;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@Slf4j
@WebServlet(name = "UpdateCarServlet", urlPatterns = "/update-car")
public class UpdateCarServlet extends HttpServlet {
    private final CarService carService = Utility.loadCarService();

    public UpdateCarServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String orderJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        CarDTO car = objectMapper.readValue(orderJson, CarDTO.class);
        boolean updateCar = carService.update(
                car.getId(),
                car.getOwnerId(),
                car.getBrand(),
                car.getModel(),
                car.getYearOfProd(),
                car.getPrice(),
                car.getCondition()
        );
        String jsonString = updateCar ? "car is updated" : "car is not updated!";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}