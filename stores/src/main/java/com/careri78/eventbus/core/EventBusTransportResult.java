package com.careri78.eventbus.core;

import io.micrometer.common.lang.Nullable;

public class EventBusTransportResult {

    public static <T> EventBusTransportResult failed(EventBusTransport transport2, PublishContext<T> ctx, Throwable error) {
        return create(
            EventBusResultCode.Failed, 
            transport2, 
            ctx, 
            error);
    }

    private static <T> EventBusTransportResult create(EventBusResultCode code, EventBusTransport transport, PublishContext<T> ctx, @Nullable Throwable e) {
        return new EventBusTransportResult(
            code,
            ctx.getMessage().getClass(),
            transport.getClass(),
            transport.getName(),
            e);
    }

    private final EventBusResultCode resultCode;
    private final Class<?> messageClass;
    private final Class<? extends EventBusTransport> transport;
    private final String name;
    @Nullable private final Throwable Throwable;
    private EventBusTransportResult(
        EventBusResultCode code, 
        Class<?> messageClass, 
        Class<? extends EventBusTransport> transport,
        String name, 
        @Nullable Throwable Throwable) {
            this.resultCode = code;
            this.messageClass = messageClass;
            this.transport = transport;
            this.name = name;
            this.Throwable = Throwable;
    }

    public EventBusResultCode getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public Class<?> getMessageClass() {
        return messageClass;
    }

    public Class<? extends EventBusTransport> getTransport() {
        return transport;
    }

    @Nullable
    public Throwable getThrowable() {
        return Throwable;
    }
}