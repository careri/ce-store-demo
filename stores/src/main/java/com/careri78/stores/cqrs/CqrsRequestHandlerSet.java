package com.careri78.stores.cqrs;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

// Creates an instance of all available handlers in the current DI scope.
@Service
@Scope("Prototype")
public final class CqrsRequestHandlerSet {
    @Autowired
    private List<? extends ValueRequestHandlerBase> valueHandlers;

    public List<? extends ValueRequestHandlerBase> getValueHandlers() {
        return valueHandlers;
    }
}
