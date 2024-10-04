package com.careri78.stores.core.repositories;


import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface EntityRepository<T, ID> extends Repository<T, ID>{    
    Optional<T> findById(ID id);
    <S extends T> S save(S entity);
}
