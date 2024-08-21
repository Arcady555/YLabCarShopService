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
import java.util.Scanner;

/**
 * Страница обновления информации о юзере
 */
@Slf4j
@WebServlet(name = "UpdateUserServlet", urlPatterns = "/update-user")
public class UpdateUserServlet extends HttpServlet {
    private final UserService userService;

    public UpdateUserServlet() throws Exception {
        userService = Utility.loadUserservice();
    }

    public UpdateUserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute("user");
        int responseStatus = user == null ? 401 : 403;
        String jsonString = "no rights or registration!";
        if (user != null && user.getRole() == UserRole.ADMIN) {
            Scanner scanner = new Scanner(request.getInputStream());
            String userJson = scanner.useDelimiter("\\A").next();
            scanner.close();
            ObjectMapper objectMapper = new ObjectMapper();
            UserAllParamDTO userDTO = objectMapper.readValue(userJson, UserAllParamDTO.class);
            boolean updateUser = userService.update(
                    userDTO.getId(),
                    userDTO.getRole(),
                    userDTO.getName(),
                    userDTO.getPassword(),
                    userDTO.getContactInfo(),
                    userDTO.getBuysAmount()
            );
            jsonString = updateUser ? "user is updated" : "user is not updated!";
            responseStatus = "user is not updated!".equals(jsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}