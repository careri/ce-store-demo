package com.careri78.stores.core.queries;

import com.careri78.cqrs.core.ValueRequest;
import com.careri78.stores.domain.OutboxEntry;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class OutboxEntriesQuery implements ValueRequest<Iterable<OutboxEntry>> {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static OutboxEntriesQuery FromName(String name) {
        OutboxEntriesQuery query = new OutboxEntriesQuery();
        query.setName(name);
        return query;
    }
}
