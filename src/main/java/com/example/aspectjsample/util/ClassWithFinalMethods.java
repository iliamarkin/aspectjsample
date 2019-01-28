package com.example.aspectjsample.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.invoke.MethodHandles;

public class ClassWithFinalMethods {

    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    public static final Integer INT = 0;

    public static String getText() {
        return "text";
    }

    public final void printHello() {
        LOG.info("Hello");
    }

    public final void printWorld() {
        LOG.info("World");
    }

    public final String throwRuntimeExceptionOrReturnHelloWorld(boolean doThrow) {
        if (doThrow) {
            throw new RuntimeException("Hello World");
        }
        return "Hello, World!";
    }

    public final String getHelloWorldString() {
        return "Hello, World!";
    }

    private String getString() {
        return "string";
    }

    public final String callPrivate() {
        return getString();
    }
}
