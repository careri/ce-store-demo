package com.careri78.stores.core.queries;

import java.util.Optional;

import com.careri78.cqrs.core.ValueRequest;
import com.careri78.stores.domain.OutboxEntry;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class OutboxEntryQuery implements ValueRequest<Optional<OutboxEntry>> {
    private Long id = -1L;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public static OutboxEntryQuery FromId(Long id) {
        OutboxEntryQuery query = new OutboxEntryQuery();
        query.setId(id);
        return query;
    }
}
