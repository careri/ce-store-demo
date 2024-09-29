package com.careri78.cqrs.core;

import java.util.concurrent.CompletableFuture;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class NoValue {
    public static final NoValue Singleton = new NoValue();

    private NoValue() {
        super();
    }

    public static CompletableFuture<NoValue> asFuture() {
        return CompletableFuture.completedFuture(Singleton);
    }
}
