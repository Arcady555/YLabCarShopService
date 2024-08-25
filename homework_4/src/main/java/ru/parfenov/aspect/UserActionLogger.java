package ru.parfenov.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import ru.parfenov.model.User;
import ru.parfenov.service.LogService;

import java.time.LocalDateTime;

@Aspect
@Slf4j
public class UserActionLogger {

    @Autowired
    private final LogService service;

    public UserActionLogger(LogService service) {
        this.service = service;
    }

    @After("execution(* *(..)) && args(request, response)")
    public void logUserAction(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (response.getStatus() == 200) {
            LocalDateTime dateTime = LocalDateTime.now();
            int userId = ((User) request.getSession().getAttribute("user")).getId();
            String action = joinPoint.getSourceLocation().getWithinType().getName();
            log.info("date time : {}, user id : {}, action : {}", dateTime, userId, action);
            service.saveLineInLog(dateTime, userId, action);
        }
    }
}
