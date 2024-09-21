package com.careri78.stores.cqrs;

import java.util.concurrent.CompletableFuture;

public interface ValueRequestHandler<TRequest extends ValueRequest<T>, T> extends ValueRequestHandlerBase {
    
    CompletableFuture<T> getAsync(TRequest query);
}
