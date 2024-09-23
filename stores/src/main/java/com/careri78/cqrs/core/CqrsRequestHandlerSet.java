package com.careri78.cqrs.core;

import java.util.Collection;

// A set of handlers.
public final class CqrsRequestHandlerSet {
    private final Collection<ValueRequestHandlerBase> valueHandlers;

    public CqrsRequestHandlerSet(Collection<ValueRequestHandlerBase> values) {
        valueHandlers = values;
    }

    public Collection<ValueRequestHandlerBase> getValueHandlers() {
        return valueHandlers;
    }
}
