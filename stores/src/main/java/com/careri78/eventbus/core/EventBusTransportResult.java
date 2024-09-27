package com.careri78.eventbus.core;


/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public class EventBusTransportResult {

    public static <T> EventBusTransportResult failed(EventBusTransport transport, PublishContext<T> ctx, Throwable error) {
        return create(
            EventBusResultCode.Failed, 
            transport, 
            ctx, 
            error);
    }

    public static <T> EventBusTransportResult success(EventBusTransport transport, PublishContext<T> ctx) {
        return create(EventBusResultCode.Success, transport, ctx, null);
    }

    public static <T> EventBusTransportResult skip(EventBusTransport transport, PublishContext<T> ctx) {
        return create(EventBusResultCode.Skipped, transport, ctx, null);
    }

    private static <T> EventBusTransportResult create(EventBusResultCode code, EventBusTransport transport, PublishContext<T> ctx, Throwable e) {
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
    private final Throwable Throwable;
    private EventBusTransportResult(
        EventBusResultCode code, 
        Class<?> messageClass, 
        Class<? extends EventBusTransport> transport,
        String name, 
        Throwable Throwable) {
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

    public Throwable getThrowable() {
        return Throwable;
    }
}