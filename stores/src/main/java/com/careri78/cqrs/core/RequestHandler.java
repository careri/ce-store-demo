package com.careri78.cqrs.core;

public interface RequestHandler<TRequest extends Request> extends ValueRequestHandler<TRequest, NoValue> {
    
}
