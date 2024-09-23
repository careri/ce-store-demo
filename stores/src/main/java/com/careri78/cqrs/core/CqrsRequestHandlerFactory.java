package com.careri78.cqrs.core;

public interface CqrsRequestHandlerFactory {

    <TRequest extends ValueRequest<T>, T> ValueRequestHandler<TRequest, T> get(TRequest request);
}
