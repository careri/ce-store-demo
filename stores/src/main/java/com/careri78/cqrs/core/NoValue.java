package com.careri78.cqrs.core;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class NoValue {
    static final NoValue Singleton = new NoValue();

    private NoValue() {
        super();
    }
}
