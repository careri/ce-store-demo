package com.careri78.stores.core.queries;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
public final class OutboxEntryQueryHandler implements ValueRequestHandler<OutboxEntryQuery, Optional<OutboxEntry>> {
    private final OutboxEntryRepository repository;

    public OutboxEntryQueryHandler(OutboxEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Optional<OutboxEntry>> getAsync(OutboxEntryQuery query) {
        final Long id = query.getId();
        Optional<OutboxEntry> entry = id > -1
            ? repository.findById(id)
            : Optional.empty();
        return CompletableFuture.completedFuture(entry);
    }
}
