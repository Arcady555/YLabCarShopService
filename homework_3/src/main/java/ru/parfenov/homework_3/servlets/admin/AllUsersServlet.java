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
import java.util.List;

/**
 * Страница вывода списков всех юзеров
 */
@Slf4j
@WebServlet(name = "AllUsersServlet", urlPatterns = "/all-users")
public class AllUsersServlet extends HttpServlet {
    private final UserService userService;

    public AllUsersServlet() throws Exception {
        userService = Utility.loadUserservice();
    }

    public AllUsersServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute("user");
        int responseStatus = user == null ? 401 : 403;
        String userListJsonString = "no rights or registration!";
        if (user != null && user.getRole() == UserRole.ADMIN) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> userList = userService.findAll();
            userListJsonString = !userList.isEmpty() ? objectMapper.writeValueAsString(userList) : "no users!";
            responseStatus = "no users!".equals(userListJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userListJsonString);
        out.flush();
    }
}