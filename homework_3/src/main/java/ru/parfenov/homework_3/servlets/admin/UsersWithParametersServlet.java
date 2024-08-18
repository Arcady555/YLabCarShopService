package ru.parfenov.homework_3.servlets.admin;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.UserService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Страница позволяет провести поиск юзеров
 * по нужным параметрам
 */
@Slf4j
@WebServlet(name = "UserWithParametersServlet", urlPatterns = "/users-with-parameters")
public class UsersWithParametersServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public UsersWithParametersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String contactInfo = request.getParameter("contactInfo");
        String buysAmount = request.getParameter("buysAmount");
        List<User> userList = userService.findByParameters(role, name, contactInfo, buysAmount);
        String userJsonString = !userList.isEmpty() ? new Gson().toJson(userList) : "no users with these parameters!";
        response.setStatus("no users with these parameters!".equals(userJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}