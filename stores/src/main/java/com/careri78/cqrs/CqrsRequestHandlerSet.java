package com.careri78.cqrs;

import java.util.Collection;

// Creates an instance of all available handlers in the current DI scope.
public final class CqrsRequestHandlerSet {
    private final Collection<ValueRequestHandlerBase> valueHandlers;

    public CqrsRequestHandlerSet(Collection<ValueRequestHandlerBase> values) {
        valueHandlers = values;
    }

    public Collection<ValueRequestHandlerBase> getValueHandlers() {
        return valueHandlers;
    }
}
