package com.careri78.cqrs;

import java.util.concurrent.CompletableFuture;

public interface ValueRequestHandler<TRequest extends ValueRequest<T>, T> extends ValueRequestHandlerBase {
    
    CompletableFuture<T> getAsync(TRequest query);
}
