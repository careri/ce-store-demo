package com.careri78.stores.cqrs;

public interface RequestHandler<TRequest extends Request> extends ValueRequestHandler<TRequest, NoValue> {
    
}
