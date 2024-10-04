package com.careri78.stores.core.commands;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.careri78.cqrs.core.NoValue;
import com.careri78.cqrs.core.RequestHandler;
import com.careri78.stores.core.services.OutboxPublishService;


/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
@Component
@Scope("prototype")
public final class ProcessOutboxCommandHandler implements RequestHandler<ProcessOutboxCommand> {
    private static final Logger log = LoggerFactory.getLogger(ProcessOutboxCommandHandler.class);

    private final OutboxPublishService service;

    public ProcessOutboxCommandHandler(final OutboxPublishService service) {
        super();
        this.service = service;
    }
    @Override
    public CompletableFuture<NoValue> getAsync(final ProcessOutboxCommand query) {
        log.debug("Executing");
        return service.publishAllAsync().thenApplyAsync(count -> NoValue.Singleton);
    }
    
}
