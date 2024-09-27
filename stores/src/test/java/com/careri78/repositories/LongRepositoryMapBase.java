package com.careri78.repositories;

import java.util.concurrent.atomic.AtomicLong;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public abstract class LongRepositoryMapBase<T> extends RepositoryMapBase<T, Long> {
    private final AtomicLong idCounter = new AtomicLong();

    public LongRepositoryMapBase(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected Long createId(final T entity) {
        final long id = idCounter.incrementAndGet();
        setId(id, entity);
        return id;
    }

    protected abstract void setId(long id, T entity);
    
}
