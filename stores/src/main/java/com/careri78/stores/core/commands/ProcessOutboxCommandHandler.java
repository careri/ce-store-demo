package com.careri78.stores.core.commands;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.careri78.cqrs.core.NoValue;
import com.careri78.cqrs.core.RequestHandler;


/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
@Component
@Scope("prototype")
public final class ProcessOutboxCommandHandler implements RequestHandler<ProcessOutboxCommand> {

    @Override
    public CompletableFuture<NoValue> getAsync(ProcessOutboxCommand query) {
        // TODO Implement the real outbox publisher using Jms
        return CompletableFuture.completedFuture(NoValue.Singleton);
    }
    
}
