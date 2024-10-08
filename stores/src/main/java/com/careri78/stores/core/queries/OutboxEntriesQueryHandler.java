package com.careri78.stores.core.queries;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.domain.OutboxEntry;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
@Component
@Scope("prototype")
public final class OutboxEntriesQueryHandler implements ValueRequestHandler<OutboxEntriesQuery, Iterable<OutboxEntry>> {
    private static final Logger log = LoggerFactory.getLogger(OutboxEntriesQueryHandler.class);
    private final OutboxEntryRepository repository;

    public OutboxEntriesQueryHandler(OutboxEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Iterable<OutboxEntry>> getAsync(OutboxEntriesQuery query) {
        final String name = query.getName();
        log.debug("Name: %s", name);
        Iterable<OutboxEntry> book = StringUtils.isNotBlank(name)
            ? repository.findByNameContainingIgnoreCase(name)
            : repository.findAll();
        return CompletableFuture.completedFuture(book);
    }
}
