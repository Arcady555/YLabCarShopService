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
 * Страница вывода списков всех юзеров
 */
@Slf4j
@WebServlet(name = "AllUsersServlet", urlPatterns = "/all-users")
public class AllUsersServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public AllUsersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> userList = userService.findAll();
        String userListJsonString = !userList.isEmpty() ? new Gson().toJson(userList) : "no users!";
        response.setStatus("no users!".equals(userListJsonString) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userListJsonString);
        out.flush();
    }
}