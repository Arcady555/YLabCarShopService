package ru.parfenov.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Класс позволяет замерить время выполнения каждого метода из блока SERVICE
 */
@Aspect
@Component
@Slf4j
public class ServiceMethodsLogger {
    @Autowired
    public ServiceMethodsLogger() {
    }

    /**
     * Метод выводит в лог время выполнение вызываемого метода
     *
     * @param joinPoint - точка выполнения метода.
     * Из неё получим название метода, в данные для лога
     */
    @Around("execution(public * ru.homework_5.service..*(..))")
    public Object getPeriodOfMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        log.info("Execution time of {}.{} :: {} ms", className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }
}