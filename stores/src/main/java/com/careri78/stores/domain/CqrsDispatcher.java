package com.careri78.stores.domain;

import java.util.concurrent.CompletableFuture;

public interface CqrsDispatcher {
    CompletableFuture<None> sendAsync(DispatchableBase dispatchable);

    <T> CompletableFuture<T> getAsync(Dispatchable<T> dispatchable);
}
