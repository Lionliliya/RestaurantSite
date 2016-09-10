package com.gmail.liliyayalovchenko.web.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class AppLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLogging.class);

    @Before("execution(* com.gmail.liliyayalovchenko.dao.hibernate.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        LOGGER.info("************************Website - Method is starting!************************");
        LOGGER.info("Method " + joinPoint.toString());
        LOGGER.info("Method : " + joinPoint.getSignature().getName());
        LOGGER.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.gmail.liliyayalovchenko.dao.hibernate.*.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        LOGGER.info("Method " + joinPoint.toString());
        LOGGER.info("Method : " + joinPoint.getSignature().getName());
        LOGGER.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        LOGGER.info("************************Website - Method is finishing!************************");
    }

    @AfterThrowing("within(com.gmail.liliyayalovchenko.dao.hibernate.*)")
    public void logExceptionsInDAO(JoinPoint joinPoint) {
        LOGGER.error("Website - Exception thrown in dao implementation Method=" + joinPoint.toString());
    }

    @AfterThrowing("within(com.gmail.liliyayalovchenko.web.controllers.*)")
    public void logExceptionsInControllers(JoinPoint joinPoint) {
        LOGGER.error("************************Website - Exception thrown in controller Method=" + joinPoint.toString() + "************************");
    }
}