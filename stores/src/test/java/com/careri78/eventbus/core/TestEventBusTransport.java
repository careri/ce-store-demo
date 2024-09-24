package com.careri78.eventbus.core;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;;

public final class TestEventBusTransport implements EventBusTransport {
    private final Collection<EventBusTransportResult> publishedEvents = java.util.Collections
                .synchronizedCollection(new java.util.ArrayList<>());
    private final String name;
    private Boolean throwExceptionOnDispatch;
    private Boolean failOnDispatch;
    private Boolean skipOnDispatch;

    public TestEventBusTransport(final String name) {
        this(name, false, false, false);
    }

    public TestEventBusTransport(final String name, final Boolean throwExceptionOnDispatch, final Boolean failOnDispatch, final Boolean skipOnDispatch) {
        this.name = name;
        this.throwExceptionOnDispatch = throwExceptionOnDispatch;
        this.failOnDispatch = failOnDispatch;
        this.skipOnDispatch = skipOnDispatch;
    }

    public Boolean getSkipOnDispatch() {
        return skipOnDispatch;
    }

    public void setSkipOnDispatch(final Boolean skipOnDispatch) {
        this.skipOnDispatch = skipOnDispatch;
    }

    public Boolean getFailOnDispatch() {
        return failOnDispatch;
    }

    public void setFailOnDispatch(final Boolean failOnDispatch) {
        this.failOnDispatch = failOnDispatch;
    }

    public void setThrowExceptionOnDispatch(final Boolean throwExceptionOnDispatch) {
        this.throwExceptionOnDispatch = throwExceptionOnDispatch;
    }

    public Boolean getThrowExceptionOnDispatch() {
        return throwExceptionOnDispatch;
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
        if (throwExceptionOnDispatch) {
            throw new IllegalStateException(String.format("Failed to dispatch %s", ctx.getId()));
        }
        final EventBusTransportResult result = failOnDispatch
            ? EventBusTransportResult.failed(this, ctx, new IllegalStateException(String.format("Failed to dispatch %s", ctx.getId())))
            : skipOnDispatch
                ? EventBusTransportResult.skip(this, ctx)
                : EventBusTransportResult.success(this, ctx);
        publishedEvents.add(result);
        return CompletableFuture.completedFuture(result);
    }
}
