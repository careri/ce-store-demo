package com.careri78.stores.cqrs;

public final class NoValue {
    static final NoValue Singleton = new NoValue();

    private NoValue() {
        super();
    }
}
