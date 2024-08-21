package ru.parfenov.homework_3.servlets.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String userJsonString;
        if (user == null || user.getRole() != UserRole.ADMIN) {
            userJsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            String userIdStr = request.getParameter("id");
            Optional<User> userOptional = userService.findById(userIdStr);
            userJsonString = userOptional.isPresent() ? objectMapper.writeValueAsString(userOptional.get()) : "user not found!";
            responseStatus = "user not found!".equals(userIdStr) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}