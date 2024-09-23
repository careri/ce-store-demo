package com.careri78.eventbus.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

public final class EventBusDefaultTests {

    @Test
    public void shouldDispatchEvent() throws InterruptedException, ExecutionException {
        TestEventBusTransport transport = new TestEventBusTransport("one");
        List<EventBusTransport> transports = List.of(transport);
        EventBus eventBus = new EventBusDefault(transports);
        String anEvent = "Created";
        String serviceName = this.getClass().getName();
        EventBusPublishResult<String> result = eventBus.publishAsync(anEvent, ctx -> ctx.setHeader("service", serviceName)).get();
        assertEquals(anEvent, result.getEvent());
        assertEquals(anEvent.getClass(), transport.getPublishedEvents().getFirst().getMessageClass());
        assertTrue(result.getSuccess());
    }
}
