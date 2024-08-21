package ru.parfenov.homework_3.servlets.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.dto.UserAllParamDTO;
import ru.parfenov.homework_3.enums.UserRole;
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
    private final UserService userService;

    public CreateUserServlet() throws Exception {
        userService = Utility.loadUserservice();
    }

    public CreateUserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String userJsonString;
        if (user == null || user.getRole() != UserRole.ADMIN) {
            userJsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            Scanner scanner = new Scanner(request.getInputStream());
            String userJson = scanner.useDelimiter("\\A").next();
            scanner.close();
            ObjectMapper objectMapper = new ObjectMapper();
            UserAllParamDTO userDTO = objectMapper.readValue(userJson, UserAllParamDTO.class);
            Optional<User> userOptional = userService.createByAdmin(
                    0,
                    userDTO.getRole(),
                    userDTO.getName(),
                    userDTO.getPassword(),
                    userDTO.getContactInfo(),
                    userDTO.getBuysAmount()
            );
            userJsonString = userOptional.isPresent() ?
                    objectMapper.writeValueAsString(userOptional.get()) :
                    "user is not created!";
            responseStatus = "user is not created!".equals(userJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}