package com.vintrace.codingchallenge.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class LoggerAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("within(com.vintrace.codingchallenge..*) && @annotation(LogAround)")
    public void writeLogBefore(JoinPoint joinPoint) throws Throwable {
        LOGGER.info("=== Start: " + this.getMessage(joinPoint));
    }

    @AfterReturning("within(com.vintrace.codingchallenge..*) && @annotation(LogAround)")
    public void writeLogAfterReturn(JoinPoint joinPoint) throws Throwable {
        LOGGER.info("=== End with success: " + this.getMessage(joinPoint));
    }

    private String getMessage(JoinPoint joinPoint) throws Throwable {
        Method interfaceMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method implementationMethod = joinPoint.getTarget().getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
        Object[] params = joinPoint.getArgs();

        String message = null;
        if (implementationMethod.isAnnotationPresent(LogAround.class)) {
            LogAround logAround = implementationMethod.getAnnotation(LogAround.class);
            message = logAround.message() + " with params " + Arrays.toString(params);
        }

        return message;
    }

}
