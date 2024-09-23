package com.careri78.eventbus.core;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;;

public final class TestEventBusTransport implements EventBusTransport {
    private final Collection<EventBusTransportResult> publishedEvents = java.util.Collections
                .synchronizedCollection(new java.util.ArrayList<>());
    private final String name;

    public TestEventBusTransport(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T> CompletableFuture<EventBusTransportResult> dispatchAsync(final PublishContext<T> ctx) {
        EventBusTransportResult result = EventBusTransportResult.success(this, ctx);
        publishedEvents.add(result);
        return CompletableFuture.completedFuture(result);
    }
}
