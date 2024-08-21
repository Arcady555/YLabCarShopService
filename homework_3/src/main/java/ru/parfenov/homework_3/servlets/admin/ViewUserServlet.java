package ru.parfenov.homework_3.servlets.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Страница вывода юзера по введённому id
 */
@Slf4j
@WebServlet(name = "ViewUserServlet", urlPatterns = "/user")
public class ViewUserServlet extends HttpServlet {
    private final UserService userService;

    public ViewUserServlet() throws Exception {
        userService = Utility.loadUserservice();
    }

    public ViewUserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute("user");
        int responseStatus = user == null ? 401 : 403;
        String userJsonString = "no rights or registration!";
        if (user != null && user.getRole() == UserRole.ADMIN) {
            ObjectMapper objectMapper = new ObjectMapper();
            String userIdStr = request.getParameter("id");
            Optional<User> userOptional = userService.findById(userIdStr);
            userJsonString = userOptional.isPresent() ? objectMapper.writeValueAsString(userOptional.get()) : "user not found!";
            responseStatus = "user not found!".equals(userJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}