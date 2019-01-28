package com.example.aspectjsample.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Aspect
public class DebugTraceAspect {

    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    @Pointcut("execution(@com.example.aspectjsample.annotation.DebugTrace * *(..))")
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut("execution(@com.example.aspectjsample.annotation.DebugTrace *.new(..))")
    public void constructorAnnotatedDebugTrace() {
    }

    @Around("methodAnnotatedWithDebugTrace()")
    public Object aroundDebugTrace(ProceedingJoinPoint joinPoint) throws Throwable {

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Class declaringType = methodSignature.getDeclaringType();
        final String methodName = methodSignature.getName();

        return printLogAndReturnResult(joinPoint, declaringType, methodName);
    }

    @Around("constructorAnnotatedDebugTrace()")
    public Object aroundDebugTraceConstructor(ProceedingJoinPoint joinPoint) throws Throwable {

        final ConstructorSignature methodSignature = (ConstructorSignature) joinPoint.getSignature();
        final Class declaringType = methodSignature.getDeclaringType();
        final String methodName = methodSignature.getName();

        return printLogAndReturnResult(joinPoint, declaringType, methodName);
    }

    private Object printLogAndReturnResult(final ProceedingJoinPoint joinPoint, final Class declaringType, final String methodName) throws Throwable {
        LOG.debug(methodName + " of " + declaringType.getName() + " is called. Date = " + new Date());
        final Object result = joinPoint.proceed();
        LOG.debug(methodName + " of " + declaringType.getName() + " is finished. Date = " + new Date());
        return result;
    }

    private Log getLogger(final ProceedingJoinPoint joinPoint, final Class declaringType) throws IllegalAccessException {
        final Optional<Field> loggerField = Arrays.stream(declaringType.getDeclaredFields())
                .filter(field -> field.getType() == Log.class)
                .findAny();

        if (loggerField.isPresent()) {
            final Field logger = loggerField.get();
            logger.setAccessible(true);
            return (Log) logger.get(joinPoint.getThis());
        } else {
            return LogFactory.getLog(declaringType);
        }
    }
}
