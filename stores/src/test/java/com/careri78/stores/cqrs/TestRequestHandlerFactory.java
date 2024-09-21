package com.careri78.stores.cqrs;

import java.util.Map;

public final class TestRequestHandlerFactory {
    private final Map<Class<?>, Object> handlerByRequest = new Map<Class<?>, Object>();

    public TestRequestHandlerFactory(ValueRequestHandlerBase... handlers) {
        for (ValueRequestHandlerBase handler : handlers) {
            addHandler(handler);
        }
    }

    private void addHandler(ValueRequestHandlerBase handler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addHandler'");
    }
}
