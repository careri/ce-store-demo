package com.careri78.repositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationException;
import org.springframework.data.repository.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A memory repository storing the entity as a byte array
 * 
 * @author Carl Ericsson
 * 
 */
public abstract class RepositoryMapBase<T, ID> implements Repository<T, ID> {
    private final Map<ID, byte[]> map = HashMap.newHashMap(0);
    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> entityClass;

    public RepositoryMapBase(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Optional<T> findById(final ID id) {
        final byte[] data = map.get(id);
        return data != null
                ? Optional.of(deserializeData(data))
                : Optional.empty();
    }

    public <S extends T> S save(final S entity) throws IllegalArgumentException {
        final ID currentId = getId(entity);
        if (map.containsKey(currentId)) {
            throw new IllegalArgumentException(String.format("Id %s is already added", currentId));
        }

        final ID id = createId(entity);
        if (map.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Id %s already exists", id));
        }
        map.put(getId(entity), serializeData(entity));
        return entity;
    }

    public void update(final T entity) {
        final ID id = getId(entity);
        if (!map.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Id %s doesn't exists", id));
        }
        map.put(getId(entity), serializeData(entity));
    }

    public T delete(final T entity) {
        final byte[] data = map.remove(getId(entity));
        return data != null
                ? entity
                : null;
    }

    protected  byte[] serializeData(final T entity) throws SerializationException {
        try {
            return mapper.writer().writeValueAsBytes(entity);
        } catch (final JsonProcessingException e) {
            throw new SerializationException("Failed to serialize entity", e);
        }
    }

    protected T deserializeData(final byte[] data) throws SerializationException {
        try {
            return mapper.reader().readValue(data, entityClass);
        } catch (final JsonProcessingException e) {
            throw new SerializationException("Failed to deserialize entity", e);
        } catch (final IOException e) {
            throw new SerializationException("Failed to deserialize entity", e);
        }

    }

    protected abstract ID createId(T entity);

    protected abstract ID getId(T entity);

    protected List<T> findItems(final Function<T, Boolean> filter) {
        return map.values()
                .stream()
                .map(data -> deserializeData(data))
                .filter(entity -> filter.apply(entity))
                .collect(Collectors.toUnmodifiableList());
    }
}
