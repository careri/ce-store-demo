package com.careri78.stores.cqrs;

import java.util.Map;

public final class TestRequestHandlerFactory {
    private final Map<Class<?>, CqrsRequestHandlerMetadata> handlerByRequest = Map.of();

    public TestRequestHandlerFactory(Class<?>... handlers) {
        for (Class<?> handler : handlers) {
            addHandler(handler);
        }
    }

    private void addHandler(Class<?> handler) {
        handlerByRequest.put(handler, CqrsRequestHandlerMetadata.createFromHandlerClass(handler));
    }
}
