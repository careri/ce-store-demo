package com.careri78.stores.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.domain.OutboxEntry;

@Repository
public interface OutboxEntryRepositoryCrud extends OutboxEntryRepository, CrudRepository<OutboxEntry, Long> {

}
