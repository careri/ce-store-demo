package com.careri78.eventbus.core;

import java.util.Map;


/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class EventBusPublishResult<T> {

    private final T event;
    private final Iterable<String> transportNames;
    private final Map<String, String> headers;
    private final Throwable error;
    
    public EventBusPublishResult(
        final T event, 
        final Iterable<String> transportNames, 
        final Map<String, String> headers,
        final Throwable error) {
        this.transportNames = transportNames;
        this.headers = headers;
        this.error = error;
        this.event = event;
    }

    public Iterable<String> getTransportNames() {
        return transportNames;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Throwable getError() {
        return error;
    }

    public Boolean getSuccess() {
        return error == null;
    }

    public T getEvent() {
        return event;
    }
}
