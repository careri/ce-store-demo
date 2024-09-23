package com.careri78.cqrs;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

@Service
final class CqrsDispatcherImpl implements CqrsDispatcher {
    
    private final CqrsRequestHandlerFactory factory;

    public CqrsDispatcherImpl(CqrsRequestHandlerFactory factory) {
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
