package com.careri78.eventbus.core;

import java.util.Collection;
import java.util.stream.Collectors;

public final class PublishContext<T> {

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

}
