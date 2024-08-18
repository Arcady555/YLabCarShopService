package ru.parfenov.homework_3.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.UserFoRegDTO;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@WebServlet(name = "SignUpServlet", urlPatterns = "/sign-up")
public class SignUpServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public SignUpServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        UserFoRegDTO userDIO = objectMapper.readValue(userJson, UserFoRegDTO.class);
        Optional<User> userOptional =
                userService.createByReg(userDIO.getName(), userDIO.getPassword(), userDIO.getContactInfo());
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