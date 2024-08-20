package ru.parfenov.homework_3.servlets.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.parfenov.homework_3.enums.UserRole;
import ru.parfenov.homework_3.model.LineInLog;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.service.LogService;
import ru.parfenov.homework_3.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Страница, где админ может найти в логе инф по клиенту, по дате и по виду операции
 */

public class LogWithParametersServlet extends HttpServlet {
    private final LogService service = Utility.loadLogService();

    public LogWithParametersServlet() throws Exception {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int responseStatus;
        var user = (User) session.getAttribute("user");
        String logJsonString;
        if (user == null || user.getRole() != UserRole.ADMIN) {
            logJsonString = "no rights or registration!";
            responseStatus = user == null ? 401 : 403;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            String userId = request.getParameter("userId");
            String action = request.getParameter("action");
            String dateTimeFom = request.getParameter("dateTimeFom");
            String dateTimeTo = request.getParameter("dateTimeTo");
            List<LineInLog> userList = service.findByParameters(userId, action, dateTimeFom, dateTimeTo);
            logJsonString = !userList.isEmpty() ? objectMapper.writeValueAsString(userList) : "no users with these parameters!";
            responseStatus = "no log with these parameters!".equals(logJsonString) ? 404 : 200;
        }
        response.setStatus(responseStatus);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(logJsonString);
        out.flush();
    }
}