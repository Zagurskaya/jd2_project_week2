package com.gmail.zagurskaya.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class DocumentAspect {
    private static final Logger logger = LogManager.getLogger(DocumentAspect.class);

    LocalDate date = LocalDate.now();

    public static String getFormattedLocalDateAndTime(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00.000"));
    }


    @Pointcut("execution(* com.gmail.zagurskaya.controller.imp.*.*(..))")
    public void CallDocumentAdd() {
    }

    @Before("CallDocumentAdd()")
    public void beforeCallAtMethodAdd(){
        logger.info("start time = "+getFormattedLocalDateAndTime(date));
    }

    @After("CallDocumentAdd()")
    public void afterCallAtMethodAdd(){
        logger.info("end time = "+getFormattedLocalDateAndTime(date));
    }
}
