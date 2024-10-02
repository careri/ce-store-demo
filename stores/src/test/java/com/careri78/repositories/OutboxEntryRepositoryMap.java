package com.careri78.repositories;

import java.util.List;
import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.domain.OutboxEntry;

import org.apache.commons.lang3.StringUtils;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class OutboxEntryRepositoryMap extends LongRepositoryMapBase<OutboxEntry> implements OutboxEntryRepository {

    public OutboxEntryRepositoryMap() {
        super(OutboxEntry.class);
    }

    @Override
    public List<OutboxEntry> findByNameContainingIgnoreCase(String name) {
        return this.findItems(book -> StringUtils.containsIgnoreCase(book.getName(), name));
    }

    @Override
    protected Long getId(OutboxEntry entity) {
        return entity.getId();
    }

    @Override
    protected void setId(long id, OutboxEntry entity) {
        entity.setId(id);
    }
}
