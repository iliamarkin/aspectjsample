package com.example.aspectjsample.aspect;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
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
public class JodaAspectTest {

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
    public void testInterceptDateTimeYearPrintsYearInLog() {
        final DateTime dateTime = new DateTime();
        final DateTime.Property year = dateTime.year();
        Mockito.verify(this.mockAppender, Mockito.atLeast(1)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent event = events.get(0);
        Assert.assertThat(event.getLevel(), CoreMatchers.is(Level.INFO));
        Assert.assertThat(event.getRenderedMessage(), CoreMatchers.is("Hello from JodaAspect. Year = " + year.get()));
    }
}
