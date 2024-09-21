package com.careri78.stores.cqrs;

public interface CqrsRequestHandlerFactory {

    <TRequest extends ValueRequest<T>, T> ValueRequestHandler<TRequest, T> get(TRequest request);
}
