package com.careri78.eventbus.core;

import java.util.concurrent.CompletableFuture;

public interface EventBusTransport {
    String getName();

    <T> CompletableFuture<EventBusTransportResult> dispatchAsync(PublishContext<T> ctx);
}
