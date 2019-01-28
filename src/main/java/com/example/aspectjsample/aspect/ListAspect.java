package com.example.aspectjsample.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import static java.lang.invoke.MethodHandles.lookup;

@Aspect
public class ListAspect {

    private static final Log LOG = LogFactory.getLog(lookup().lookupClass());

    @Pointcut("call(* java.util.List.add(..)) && args(o)")
    public void add(Object o) {}

    @After(value = "add(o)", argNames = "joinPoint,o")
    public void interceptAdd(JoinPoint joinPoint, Object o) {
        LOG.info("Hello from ListAspect. Added value = " + o);
    }
}
