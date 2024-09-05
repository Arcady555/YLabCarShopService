package ru.homework_5.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.homework_5.service.LogService;

import java.time.LocalDateTime;

import static ru.homework_5.utility.Utility.getPersonId;

/**
 * Класс записывает в лог действия юзеров (вызванные с их подачи методы классов блока CONTROLLER)
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserActionLogger {
    @Autowired
    private final LogService service;

    /**
     * Метод принимает информацию(время события, ID юзера и название его действия)
     * <p>
     * <p>
     * и отправляет её по ДВУМ КАНАЛАМ - в журнал логов и в БД
     *
     * @param joinPoint точка выполнения метода.
     */
    @AfterReturning("execution(public * ru.homework_5.controller..*(..))")
    public void logUserAction(JoinPoint joinPoint) {
        LocalDateTime dateTime = LocalDateTime.now();
        int personId = getPersonId();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String action = methodSignature.getDeclaringType().getSimpleName() + "." +
                methodSignature.getName();
        log.info("date time : {}, user id : {}, action : {}", dateTime, personId, action);
        service.saveLineInLog(dateTime, personId, action);
    }
}