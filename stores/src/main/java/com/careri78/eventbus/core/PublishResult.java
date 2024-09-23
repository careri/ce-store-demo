package com.careri78.eventbus.core;

public final class PublishResult<T> {

    private final T event;
    public PublishResult(T event) {
        this.event = event;
    }

    public T getEvent() {
        return event;
    }

}
