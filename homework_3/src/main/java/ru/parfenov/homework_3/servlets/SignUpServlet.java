package ru.parfenov.homework_3.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.UserNamePasContDTO;
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
    private final UserService userService;

    public SignUpServlet() throws Exception {
        this.userService = Utility.loadUserservice();
    }

    public SignUpServlet(UserService userService) throws Exception {
        this.userService = userService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        UserNamePasContDTO userDIO = objectMapper.readValue(userJson, UserNamePasContDTO.class);
        Optional<User> userOptional =
                userService.createByReg(userDIO.getName(), userDIO.getPassword(), userDIO.getContactInfo());
        String userJsonString = userOptional.isPresent() ?
                objectMapper.writeValueAsString(userOptional.get()) :
                "user is not created!";
        response.setStatus("user is not created!".equals(userJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}