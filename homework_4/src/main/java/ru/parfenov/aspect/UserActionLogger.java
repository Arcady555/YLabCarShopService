package ru.parfenov.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.parfenov.model.User;
import ru.parfenov.service.LogService;
import ru.parfenov.service.impl.LogServiceServletImpl;

import java.time.LocalDateTime;

/**
 * Класс записывает в лог действия юзеров (вызванные с их подачи методы классов блока CONTROLLER)
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class UserActionLogger {
    private final LogService service;

    @Pointcut("execution(public ru.parfenov.controller..*")
    public void pickMethods() {
    }

    /**
     * Метод принимает информацию(время события, ID юзера и название его действия)
     *
     *
     * и отправляет её по ДВУМ КАНАЛАМ - в журнал логов и в БД
     *
     *
     * @param joinPoint  точка выполнения метода.
     * @param request запрос клиента
     * @param response ответ сервера
     */
    @After("execution(* *(..)) && args(request, response)")
    public void logUserAction(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) {
        if (response.getStatus() == HttpStatus.OK.value()) {
            LocalDateTime dateTime = LocalDateTime.now();
            int userId = ((User) request.getSession().getAttribute("user")).getId();
            String action = joinPoint.getSourceLocation().getWithinType().getName();
            log.info("date time : {}, user id : {}, action : {}", dateTime, userId, action);
            service.saveLineInLog(dateTime, userId, action);
        }
    }
}
