package ru.parfenov.homework_3.servlets.admin;

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

@Slf4j
@WebServlet(name = "DeleteUserServlet", urlPatterns = "/delete-user")
public class DeleteUserServlet extends HttpServlet {
    private final UserService userService = Utility.loadUserservice();

    public DeleteUserServlet() throws Exception {
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String jsonString;
        if (user == null || user.getRole() != UserRole.ADMIN) {
            jsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            String userIdStr = request.getParameter("id");
            jsonString = userService.delete(userIdStr) ? "the user is deleted" : "the user is not deleted!";
            responseStatus = "the user is not deleted!".equals(jsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}
