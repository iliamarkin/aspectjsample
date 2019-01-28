package com.example.aspectjsample.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;

import static java.lang.invoke.MethodHandles.lookup;

@Aspect
public class JodaAspect {

    private static final Log LOG = LogFactory.getLog(lookup().lookupClass());

    @Pointcut("execution(* org.joda.time.DateTime.year())")
    public void dateTimeYear(){}

    @Around(value = "dateTimeYear()", argNames = "joinPoint")
    public Object interceptDateTimeYear(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result = joinPoint.proceed();
        LOG.info("Hello from JodaAspect. Year = " + ((DateTime.Property)result).get());
        return result;
    }
}
