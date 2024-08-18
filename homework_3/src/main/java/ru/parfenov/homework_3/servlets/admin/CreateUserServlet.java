package ru.parfenov.homework_3.servlets.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.UserForAdminDTO;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

/**
 * Страница, где админ может сам создать любого юзера и с нужным профилем
 */
@Slf4j
@WebServlet(name = "CreateUserServlet", urlPatterns = "/create-user")
public class CreateUserServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public CreateUserServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        UserForAdminDTO userDTO = objectMapper.readValue(userJson, UserForAdminDTO.class);
        Optional<User> userOptional = userService.createByAdmin(
                0,
                userDTO.getRole(),
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getContactInfo(),
                userDTO.getBuysAmount()
        );
        String userJsonString = userOptional.isPresent() ?
                new Gson().toJson(userOptional.get()) :
                "user is not created!";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}