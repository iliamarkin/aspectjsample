package com.example.aspectjsample.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import static java.lang.invoke.MethodHandles.lookup;

@Aspect
public class FinalMethodsAspect {

    private static final Log LOG = LogFactory.getLog(lookup().lookupClass());

    @Pointcut("execution(* com.example.aspectjsample.util.ClassWithFinalMethods.printHello())")
    public void printHello() {
    }

    @Pointcut("execution(* com.example.aspectjsample.util.ClassWithFinalMethods.printWorld())")
    public void printWorld() {
    }

    @Pointcut("execution(* com.example.aspectjsample.util.ClassWithFinalMethods.throwRuntimeExceptionOrReturnHelloWorld(*)) && args(doThrow)")
    public void throwRuntimeExceptionOrReturnHelloWorld(boolean doThrow) {
    }

    @Pointcut("execution(* com.example.aspectjsample.util.ClassWithFinalMethods.getHelloWorldString())")
    public void getHelloWorldString() {
    }

    @Pointcut("execution(* com.example.aspectjsample.util.ClassWithFinalMethods.getString())")
    public void getString() {
    }

    @Pointcut("execution(* com.example.aspectjsample.util.ClassWithFinalMethods.getText())")
    public void getText() {
    }

    @Pointcut("set(* com.example.aspectjsample.util.ClassWithFinalMethods.INT)")
    public void setInt() {
    }

    @Before(value = "printHello()", argNames = "joinPoint")
    public void interceptPrintHello(JoinPoint joinPoint) {
        LOG.info("@Before: hello from FinalMethodsAspect");
    }

    @After(value = "printWorld()", argNames = "joinPoint")
    public void interceptPrintWorld(JoinPoint joinPoint) {
        LOG.info("@After: hello from FinalMethodsAspect");
    }

    @AfterReturning(pointcut = "throwRuntimeExceptionOrReturnHelloWorld(b)", argNames = "joinPoint,b")
    public void interceptThrowRuntimeExceptionOrReturnHelloWorld(JoinPoint joinPoint, boolean b) {
        LOG.info("@AfterReturning: hello from FinalMethodsAspect, param value = " + b);
    }

    @AfterThrowing(pointcut = "throwRuntimeExceptionOrReturnHelloWorld(b)", argNames = "joinPoint,b")
    public void interceptThrowRuntimeExceptionOrReturnHelloWorldThrowing(JoinPoint joinPoint, boolean b) {
        LOG.info("@AfterThrowing: hello from FinalMethodsAspect, param value = " + b);
    }

    @Around(value = "getHelloWorldString()")
    public Object interceptGetHelloWorldString(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return "@Around: ";
    }

    @Around(value = "getString()")
    public Object interceptGetString(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return "@Around for private: " + proceedingJoinPoint.proceed();
    }

    @Around(value = "getText()")
    public Object interceptGetText(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return "@Around for static: " + proceedingJoinPoint.proceed();
    }

    @Around(value = "setInt()", argNames = "proceedingJoinPoint,thisJoinPointStaticPart")
    public Object interceptGetInt(ProceedingJoinPoint proceedingJoinPoint, JoinPoint.StaticPart thisJoinPointStaticPart) throws Throwable {
        System.out.println(thisJoinPointStaticPart);
        return proceedingJoinPoint.proceed(new Object[]{1});
    }
}
