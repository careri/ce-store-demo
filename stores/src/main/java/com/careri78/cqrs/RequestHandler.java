package com.careri78.cqrs;

public interface RequestHandler<TRequest extends Request> extends ValueRequestHandler<TRequest, NoValue> {
    
}
