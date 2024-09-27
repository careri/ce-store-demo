package com.careri78.eventbus.core;

import java.util.List;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public class EventBusTransportException extends RuntimeException {

    private final List<Throwable> errors;

    public EventBusTransportException(final String message, final List<Throwable> errors) {
        super(message);
        this.errors = errors;
    }

    public List<Throwable> getErrors() {
        return errors;
    }

}
