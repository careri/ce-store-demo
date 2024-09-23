package com.careri78.eventbus.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.micrometer.common.lang.Nullable;

public final class PublishContext<T> {
    private final Map<String, String> headers = java.util.Collections.emptyMap();
    private final T message;
    private final Collection<String> transportNames;

    private PublishContext(T message, Collection<String> transportNames) {
        this.message = message;
        this.transportNames = transportNames;
    }

    public T getMessage() {
        return message;
    }

    public Iterable<String> getTransportNames() {
        return transportNames;
    }

    public static <T> PublishContext<T> create(T message, Collection<EventBusTransport> transports) {
        return new PublishContext<T>(
                message,
                transports.stream().map(t -> t.getName()).collect(Collectors.toList()));
    }

    @Nullable
    public String getHeader(String key) {
        Optional<String> keyMatch = headers
            .keySet()
            .stream()
            .filter(k -> k.equalsIgnoreCase(key))
            .findFirst();
        if (keyMatch.isPresent()) {
            return headers.get(keyMatch.get());
        }

        return null;
    }

    public void setHeader(String key, @Nullable String value) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException(String.format("'%s' key can't be empty", key));
        }

        List<String> keyMatches = headers.keySet().stream().filter(k -> k.equalsIgnoreCase(key))
                .collect(Collectors.toList());
        for (String k : keyMatches) {
            headers.remove(k);
        }

        if (value != null && value.isBlank()) {
            headers.put(key, value);
        }
    }
}
