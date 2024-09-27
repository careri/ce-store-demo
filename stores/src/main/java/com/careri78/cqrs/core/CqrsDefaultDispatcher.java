package com.careri78.cqrs.core;

import java.util.concurrent.CompletableFuture;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class CqrsDefaultDispatcher implements CqrsDispatcher {
    
    private final CqrsRequestHandlerFactory factory;

    public CqrsDefaultDispatcher(CqrsRequestHandlerFactory factory) {
        this.factory = factory;

    }

    @Override
    public <TRequest extends ValueRequest<NoValue>> CompletableFuture<NoValue> sendAsync(TRequest request) {
        return getAsync(request);
    }

    @Override
    public <TRequest extends ValueRequest<T>, T> CompletableFuture<T> getAsync(TRequest request) {
        ValueRequestHandler<TRequest, T> handler = factory.get(request);
        return handler.getAsync(request);
    }
}
