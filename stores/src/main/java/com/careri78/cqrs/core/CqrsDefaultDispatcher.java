package com.careri78.cqrs.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class CqrsDefaultDispatcher implements CqrsDispatcher {
    private static final Logger log = LoggerFactory.getLogger(CqrsDefaultDispatcher.class);
    
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
        log.debug("Dispatching %s to %s", request, handler);
        return handler.getAsync(request);
    }
}
