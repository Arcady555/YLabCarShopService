package ru.parfenov.homework_3.servlets.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.UserForAdminDTO;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Страница обновления информации о юзере
 */
@Slf4j
@WebServlet(name = "UpdateUserServlet", urlPatterns = "/update-user")
public class UpdateUserServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public UpdateUserServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        UserForAdminDTO user = objectMapper.readValue(userJson, UserForAdminDTO.class);
        boolean updateUser = userService.update(
                user.getId(),
                user.getRole(),
                user.getName(),
                user.getPassword(),
                user.getContactInfo(),
                user.getBuysAmount()
        );
        String jsonString = updateUser ? "car is updated" : "car is not updated!";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}