package com.careri78.eventbus.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

public final class EventBusDefaultTests {

    @Test
    public void shouldDispatchEvent() throws InterruptedException, ExecutionException {
        List<EventBusTransport> transports = List.of(new TestEventBusTransport("one"));
        EventBus eventBus = new EventBusDefault(transports);
        String anEvent = "Created";
        PublishResult<String> result = eventBus.publishAsync(anEvent, ctx -> ctx.setHeader("service", this.getClass().getName())).get();
        assertEquals(anEvent, result.getEvent());
    }
}
