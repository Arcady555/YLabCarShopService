package ru.aspect_module.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Класс позволяет замерить время выполнения каждого метода из блока SERVICE
 */

@Aspect
@Slf4j
public class ServiceMethodsLogger {

    @Autowired
    public ServiceMethodsLogger() {
    }

    /**
     * Метод определил, какие методы будут собраны для среза
     * (в данном случае - все из блока SERVICE)
     */
    @Pointcut("execution(public homework_5/src/main/java/ru/parfenov/service..*")
    public void pickMethods() {
    }

    /**
     * Метод выводит в лог время выполнение вызываемого метода
     * @param joinPoint - точка выполнения метода.
     * Из неё получим название метода, в данные для лога
     */
    @Around("execution(* *(..))")
    public void getPeriodOfMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LocalTime timeOfStart = LocalTime.now();
        try {
            joinPoint.proceed();
            log.info("Period of method {} {}", methodName, Duration.between(LocalTime.now(), timeOfStart));
        } catch (Throwable e) {
            log.error("Exception:", e);
        }
    }
}