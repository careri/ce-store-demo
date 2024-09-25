package com.careri78.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.repository.Repository;

public abstract class RepositoryMapBase<T, ID> implements Repository<T, ID> {
    private final Map<ID, T> map = HashMap.newHashMap(0);

    public Optional<T> findById(ID id) {
        T t = map.get(id);
        return t != null
            ? Optional.of(t)
            : Optional.empty();
    }

    public <S extends T> S save(S entity) throws IllegalArgumentException {
        ID id = createId(entity);
        if (map.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Id % already exists", id));
        }
        map.put(getId(entity), entity);
        return entity;
    }

    public void update(T entity) {
        ID id = getId(entity);
        if (!map.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Id % doesn't exists", id));
        }
        map.put(getId(entity), entity);
    }

    public T delete(T entity) {
        return map.remove(getId(entity));
    }

    protected abstract ID createId(T entity);
    protected abstract ID getId(T entity);

    protected List<T> findItems(Function<T, Boolean> filter) {
        return map.values()
            .stream()
            .filter(i -> filter.apply(i))
            .collect(Collectors.toUnmodifiableList());
    }
}
