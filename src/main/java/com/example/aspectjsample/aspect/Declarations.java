package com.example.aspectjsample.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareError;
import org.aspectj.lang.annotation.DeclareWarning;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Declarations {

    @Pointcut("execution(* com.example.aspectjsample.dao.DAO+.*(..)) && !within(com.example.aspectjsample.dao..*)")
    public void badDaoImplementors() {
    }

    @Pointcut("call(* javax.sql..*(..)) && !within(com.example.aspectjsample.dao..*)")
    public void javaxSqlCall() {
    }

    @DeclareWarning(value = "javaxSqlCall()")
    static final String WRONG_JAVAX_SQL_CALL = "Only DAOs should be calling JDBC.";

    @DeclareError(value = "badDaoImplementors()")
    public static final String DAO_BAD_IMPLEMENTORS = "Only dao types can implement DAO!";
}
