package com.careri78.eventbus.core;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;;

public final class TestEventBusTransport implements EventBusTransport {
    private final Collection<EventBusTransportResult> publishedEvents = java.util.Collections
                .synchronizedCollection(new java.util.ArrayList<>());
    private final String name;

    public TestEventBusTransport(final String name) {
        this.name = name;
    }

    public List<EventBusTransportResult> getPublishedEvents() {
        return publishedEvents.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T> CompletableFuture<EventBusTransportResult> dispatchAsync(final PublishContext<T> ctx) {
        final EventBusTransportResult result = EventBusTransportResult.success(this, ctx);
        publishedEvents.add(result);
        return CompletableFuture.completedFuture(result);
    }
}
