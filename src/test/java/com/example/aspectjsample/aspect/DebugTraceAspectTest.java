package com.example.aspectjsample.aspect;

import com.example.aspectjsample.util.Utils;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DebugTraceAspectTest {

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
    public void testDebugTraceMethod() {
        Utils.sleep(1);
        verifyLog();
    }

    @Test
    public void testDebugTraceConstructor() {
        new Utils();
        verifyLog();
    }

    private void verifyLog() {
        Mockito.verify(this.mockAppender, Mockito.atLeast(2)).doAppend(this.captorLoggingEvent.capture());
        final List<LoggingEvent> events = this.captorLoggingEvent.getAllValues();
        final LoggingEvent firstEvent = events.get(0);
        final LoggingEvent lastEvent = events.get(events.size() - 1);
        Assert.assertThat(firstEvent.getLevel(), CoreMatchers.is(Level.DEBUG));
        Assert.assertThat(firstEvent.getRenderedMessage(), JUnitMatchers.containsString(" is called. Date = "));
        Assert.assertThat(lastEvent.getLevel(), CoreMatchers.is(Level.DEBUG));
        Assert.assertThat(lastEvent.getRenderedMessage(), JUnitMatchers.containsString(" is finished. Date = "));
    }
}
