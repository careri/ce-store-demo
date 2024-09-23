package com.careri78.cqrs.core;

public final class NoValue {
    static final NoValue Singleton = new NoValue();

    private NoValue() {
        super();
    }
}
