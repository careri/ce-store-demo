package com.careri78.eventbus.core;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface EventBus {
    <T> CompletableFuture<EventBusPublishResult<T>> publishAsync(T message, Consumer<PublishContext<T>> initContext);

    <T> CompletableFuture<EventBusPublishResult<T>> publishAsync(T message);
}
