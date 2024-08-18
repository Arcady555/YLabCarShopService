package ru.parfenov.homework_3.servlets.admin;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final UserService userService = Utility.loadUserservice();

    public ViewUserServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdStr = request.getParameter("id");
        Optional<User> userOptional = userService.findById(userIdStr);
        String userJsonString = userOptional.isPresent() ? new Gson().toJson(userOptional.get()) : "user not found!";
        response.setStatus("user not found!".equals(userIdStr) ? 404 : 200);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }
}