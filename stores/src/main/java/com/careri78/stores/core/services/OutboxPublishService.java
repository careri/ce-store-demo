package com.careri78.stores.core.services;


import java.util.concurrent.CompletableFuture;

public interface OutboxPublishService {
    
    /**
     * Publish any items in the outbox.
     * @return The number of items published.
     */
    public CompletableFuture<Integer> publishAllAsync();
}
