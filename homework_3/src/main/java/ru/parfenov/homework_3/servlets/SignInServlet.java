package ru.parfenov.homework_3.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.UserDTOMapper;
import ru.parfenov.homework_3.dto.UserDTOMapperImpl;
import ru.parfenov.homework_3.dto.UserIdPassDTO;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@WebServlet(name = "SignInServlet", urlPatterns = "/sign-in")
public class SignInServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public SignInServlet() throws Exception {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDTOMapper mapper = new UserDTOMapperImpl();
        Scanner scanner = new Scanner(request.getInputStream());
        String userJson = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        UserIdPassDTO userDTO = objectMapper.readValue(userJson, UserIdPassDTO.class);
        Optional<User> userOptional =
                userService.findByIdAndPassword(userDTO.getId(), userDTO.getPassword());
        String userJsonString;
        int responseStatus;
        if (userOptional.isPresent()) {
            userJsonString = objectMapper.writeValueAsString(mapper.toUserIdNameRoleDTO(userOptional.get()));
            responseStatus = 200;
            var session = request.getSession();
            session.setAttribute("user", userOptional.get());
        } else {
            userJsonString = "user is not found!";
            responseStatus = 404;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}