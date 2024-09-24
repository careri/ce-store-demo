package com.careri78.eventbus.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.micrometer.common.lang.Nullable;

public final class PublishContext<T> {
    public static <T> PublishContext<T> create(final T message, final Collection<EventBusTransport> transports) {
        return new PublishContext<T>(
                message,
                transports.stream().map(t -> t.getName()).collect(Collectors.toList()));
    }
    private final Map<String, String> headers = java.util.Collections.emptyMap();
    private final T message;
    private final Collection<String> transportNames;
    private final UUID id;

    private PublishContext(final T message, final Collection<String> transportNames) {
        this.message = message;
        this.transportNames = transportNames;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public T getMessage() {
        return message;
    }

    public Iterable<String> getTransportNames() {
        return transportNames;
    }

    @Nullable
    public String getHeader(final String key) {
        final Optional<String> keyMatch = headers
            .keySet()
            .stream()
            .filter(k -> k.equalsIgnoreCase(key))
            .findFirst();
        if (keyMatch.isPresent()) {
            return headers.get(keyMatch.get());
        }

        return null;
    }

    public void setHeader(final String key, @Nullable final String value) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException(String.format("'%s' key can't be empty", key));
        }

        final List<String> keyMatches = headers.keySet().stream().filter(k -> k.equalsIgnoreCase(key))
                .collect(Collectors.toList());
        for (final String k : keyMatches) {
            headers.remove(k);
        }

        if (value != null && value.isBlank()) {
            headers.put(key, value);
        }
    }

    public Map<String, String> getHeaders() {
        return headers.entrySet().stream().map(e -> e).collect(Collectors.toUnmodifiableMap(e -> e.getKey(), e -> e.getValue()));
    }
}
