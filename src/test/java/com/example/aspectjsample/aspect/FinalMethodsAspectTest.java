package com.example.aspectjsample.aspect;

import com.example.aspectjsample.util.ClassWithFinalMethods;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FinalMethodsAspectTest {

    @Mock
    private Appender mockAppender;
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    private final ClassWithFinalMethods classWithFinalMethods = new ClassWithFinalMethods();

    @Before
    public void setup() {
        LogManager.getRootLogger().addAppender(this.mockAppender);
    }

    @After
    public void teardown() {
        LogManager.getRootLogger().removeAppender(this.mockAppender);
    }

    @Test
    public void testGetHelloWorldStringReturnsWithAdditionalString() {
        Assert.assertEquals("@Around: ", this.classWithFinalMethods.getHelloWorldString());
    }

    @Test
    public void testCallPrivateReturnsWithAdditionalString() {
        Assert.assertEquals("@Around for private: string", this.classWithFinalMethods.callPrivate());
    }

    @Test
    public void testGetTextReturnsWithAdditionalString() {
        Assert.assertEquals("@Around for static: text", ClassWithFinalMethods.getText());
    }

    @Test
    public void testIntConstantHasValueFromAspect() {
        Assert.assertEquals(Integer.valueOf(1), ClassWithFinalMethods.INT);
    }


    @Test
    public void testInterceptPrintHelloPrintsLogBeforeExecuting() {
        this.classWithFinalMethods.printHello();
        Mockito.verify(this.mockAppender, Mockito.atLeast(1)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent event = events.get(0);
        Assert.assertThat(event.getLevel(), CoreMatchers.is(Level.INFO));
        Assert.assertThat(event.getRenderedMessage(), CoreMatchers.is("@Before: hello from FinalMethodsAspect"));
    }

    @Test
    public void testInterceptPrintWorldPrintsLogAfterExecuting() {
        this.classWithFinalMethods.printWorld();
        Mockito.verify(this.mockAppender, Mockito.atLeast(1)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent event = events.get(events.size() - 1);
        Assert.assertThat(event.getLevel(), CoreMatchers.is(Level.INFO));
        Assert.assertThat(event.getRenderedMessage(), CoreMatchers.is("@After: hello from FinalMethodsAspect"));
    }

    @Test
    public void testInterceptThrowRuntimeExceptionOrReturnHelloWorldPrintsLogAfterReturning() {
        this.classWithFinalMethods.throwRuntimeExceptionOrReturnHelloWorld(false);
        Mockito.verify(this.mockAppender, Mockito.atLeast(1)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent event = events.get(events.size() - 1);
        Assert.assertThat(event.getLevel(), CoreMatchers.is(Level.INFO));
        Assert.assertThat(event.getRenderedMessage(), CoreMatchers.is("@AfterReturning: hello from FinalMethodsAspect, param value = false"));
    }

    @Test(expected = RuntimeException.class)
    public void testInterceptThrowRuntimeExceptionOrReturnHelloWorldThrowingPrintsLogAfterReturning() {
        this.classWithFinalMethods.throwRuntimeExceptionOrReturnHelloWorld(true);
        Mockito.verify(this.mockAppender, Mockito.atLeast(1)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent event = events.get(events.size() - 1);
        Assert.assertThat(event.getLevel(), CoreMatchers.is(Level.INFO));
        Assert.assertThat(event.getRenderedMessage(), CoreMatchers.is("@AfterThrowing: hello from FinalMethodsAspect, param value = true"));
    }
}
