package com.careri78.cqrs.core;

import java.util.concurrent.CompletableFuture;

public interface CqrsDispatcher {
    <TRequest extends ValueRequest<NoValue>> CompletableFuture<NoValue> sendAsync(TRequest request);

    <TRequest extends ValueRequest<T>, T> CompletableFuture<T> getAsync(TRequest request);
}
