package com.example.aspectjsample.aspect;

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

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ListAspectTest {

    @Mock
    private Appender mockAppender;
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setup() {
        LogManager.getRootLogger().addAppender(this.mockAppender);
    }

    @After
    public void teardown() {
        LogManager.getRootLogger().removeAppender(this.mockAppender);
    }

    @Test
    public void testAddPrintsLogAfterAdding() {
        final List<String> list = new ArrayList<>();
        final String value = "test123";
        list.add(value);
        Mockito.verify(this.mockAppender, Mockito.atLeast(1)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent event = events.get(0);
        Assert.assertThat(event.getLevel(), CoreMatchers.is(Level.INFO));
        Assert.assertThat(event.getRenderedMessage(), CoreMatchers.is("Hello from ListAspect. Added value = " + value));
    }
}
