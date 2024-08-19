package ru.parfenov.homework_3.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Aspect
@Slf4j
public class ServiceMethodsLogger {

    @Pointcut("execution(public ru.parfenov.homework_3.service..*")
    public void userServiceServletImplUpdate()  {
    }

    @After("execution(* *(..))")
    public void logCallMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Call method {} {}", methodName, LocalDateTime.now());
    }
}