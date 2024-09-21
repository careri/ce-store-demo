package com.careri78.stores.cqrs;

public final class GetIntQuery implements ValueRequest<Integer> {
    private int value;

    public GetIntQuery(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
