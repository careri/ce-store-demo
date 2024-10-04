package com.careri78.stores.core.repositories;


import java.util.Optional;

import com.careri78.stores.domain.OutboxEntry;

public interface OutboxEntryRepository extends EntityRepository<OutboxEntry, Long>{
    Iterable<OutboxEntry> findAll();
    Optional<OutboxEntry> findById(Long id);
    void deleteById(Long id);
    Iterable<OutboxEntry> findByNameContainingIgnoreCase(String name);
}
