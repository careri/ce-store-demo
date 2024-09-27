package com.careri78.eventbus.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class EventBusDefaultTests {

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
    class DispatchResult<TEvent> {

        private final String serviceName;
        private final EventBusPublishResult<TEvent> result;
        private final TEvent event;
        private final List<TestEventBusTransport> transports;

        public DispatchResult(
            final String serviceName, 
            final EventBusPublishResult<TEvent> result, 
            final TEvent event,
            final List<TestEventBusTransport> transports) {
            this.serviceName = serviceName;
            this.result = result;
            this.event = event;
            this.transports = transports;
        }

        public String getServiceName() {
            return serviceName;
        }

        public EventBusPublishResult<TEvent> getResult() {
            return result;
        }

        public TEvent getEvent() {
            return event;
        }

        public List<TestEventBusTransport> getTransports() {
            return transports;
        }

    }

    @Test
    public void shouldDispatchEvent() throws InterruptedException, ExecutionException {
        DispatchResult<String> result = dispatchEvent("Success", new TestEventBusTransport("one"));
        TestEventBusTransport transport = result.transports.getFirst();
        assertEquals(result.getEvent().getClass(), transport.getPublishedEvents().getFirst().getMessageClass());
        assertTrue(result.getResult().getSuccess());
    }

    @Test
    public void shouldDispatchToMultipleTransportsEvent() throws InterruptedException, ExecutionException {
        DispatchResult<String> result = dispatchEvent("Success", new TestEventBusTransport("one"), new TestEventBusTransport("two"));
        assertTrue(result.getTransports().stream().allMatch(t -> !t.getPublishedEvents().isEmpty()));
        assertTrue(result.getResult().getSuccess());
    }

    @Test
    public void shouldFailToDispatchEvent() throws InterruptedException, ExecutionException {
        DispatchResult<String> result = dispatchEvent("Failing", new TestEventBusTransport("one", false, true, false));
        TestEventBusTransport transport = result.transports.getFirst();
        assertEquals(EventBusResultCode.Failed, transport.getPublishedEvents().getFirst().getResultCode());
        assertFalse(result.getResult().getSuccess());
    }

    @Test
    public void shouldFailDueToThrowWhenDispatchingEvent() throws InterruptedException, ExecutionException {
        DispatchResult<String> result = dispatchEvent("Throwing", new TestEventBusTransport("one", true, false, false));
        TestEventBusTransport transport = result.transports.getFirst();
        assertTrue(transport.getPublishedEvents().isEmpty());
        assertFalse(result.getResult().getSuccess());
    }

    private <TEvent> DispatchResult<TEvent> dispatchEvent(final TEvent anEvent, final TestEventBusTransport ...transports) throws InterruptedException, ExecutionException {
        final List<TestEventBusTransport> transportsList = Arrays.stream(transports).collect(Collectors.toUnmodifiableList());
        final EventBus eventBus = new EventBusDefault(Arrays.stream(transports).collect(Collectors.toUnmodifiableList()));
        final String serviceName = this.getClass().getName();
        final EventBusPublishResult<TEvent> result = eventBus.publishAsync(anEvent, ctx -> ctx.setHeader("service", serviceName)).get();
        assertEquals(anEvent, result.getEvent());
        return new DispatchResult<>(
            serviceName,
            result,
            anEvent,
            transportsList);
    }
}
