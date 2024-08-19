package ru.parfenov.homework_3.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.parfenov.homework_3.model.LineInLog;
import ru.parfenov.homework_3.model.User;
import ru.parfenov.homework_3.store.LogStore;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Aspect
@Slf4j
public class UserActionLogger {
    private final LogStore logStore = new LogStore();

    public UserActionLogger() throws Exception {
    }

    @Pointcut("execution(* ru.parfenov.homework_3.servlets..*")
    public void closeOrderServletDoGet() {
    }

    @After("execution(* *(..)) && args(request, response)")
    public void logUserAction(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (response.getStatus() == 200) {
            LocalDateTime dateTime = LocalDateTime.now();
            int userId = ((User) request.getSession().getAttribute("user")).getId();
            String action = joinPoint.getSourceLocation().getWithinType().getName();
            log.info("date time : {}, user id : {}, action : {}", dateTime, userId, action);
            LineInLog lineInLog = new LineInLog(
                    0L,
                    dateTime.truncatedTo(ChronoUnit.MINUTES),
                    Integer.toString(userId),
                    action
            );
            logStore.create(lineInLog);
        }
    }
}
