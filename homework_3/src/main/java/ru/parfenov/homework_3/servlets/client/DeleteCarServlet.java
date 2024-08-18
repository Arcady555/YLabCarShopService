package ru.parfenov.homework_3.servlets.client;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.service.CarService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(name = "DeleteCarServlet", urlPatterns = "/delete-car")
public class DeleteCarServlet extends HttpServlet {
    private final CarService carService = Utility.loadCarService();

    public DeleteCarServlet() throws Exception {
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carIdStr = request.getParameter("id");
        String jsonString = carService.delete(carIdStr) ? "the car is deleted" : "the car is not deleted!";
        response.setStatus("the car is not deleted!".equals(jsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}
