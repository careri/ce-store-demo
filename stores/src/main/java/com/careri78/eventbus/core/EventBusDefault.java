package com.careri78.eventbus.core;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import io.micrometer.common.lang.Nullable;

public final class EventBusDefault implements EventBus {

    private final Collection<EventBusTransport> transports;

    public EventBusDefault(final Collection<EventBusTransport> transports) {
        this.transports = transports;
    }

    @Override
    public <T> CompletableFuture<EventBusPublishResult<T>> publishAsync(final T message,
            final Consumer<PublishContext<T>> initContext) {
        final PublishContext<T> ctx = PublishContext.create(message, transports);
        initContext.accept(ctx);
        final List<TransportTask<T>> tasks = transports
                .stream()
                .map(t -> new TransportTask<>(t, ctx))
                .collect(Collectors.toUnmodifiableList());
        final List<CompletableFuture<EventBusTransportResult>> restultFutures = tasks
                .stream()
                .map(t -> t.getResultAsync())
                .collect(Collectors.toUnmodifiableList());
        final CompletableFuture<Collection<EventBusTransportResult>> resultsFuture = getResultsAsync(restultFutures);
        return resultsFuture
                .thenApply(r -> createSuccessResult(ctx, r))
                .exceptionally(e -> createFailedResult(ctx, e));
    }

    private <T> EventBusPublishResult<T> createSuccessResult(PublishContext<T> ctx, Collection<EventBusTransportResult> transportResults) {
        List<Throwable> errors = transportResults
            .stream()
            .filter(result -> result.getResultCode() == EventBusResultCode.Failed)
            .map(result -> result.getThrowable())
            .collect(Collectors.toUnmodifiableList());
        if (errors.isEmpty()) {
            return new EventBusPublishResult<T>(ctx.getMessage(), ctx.getTransportNames(), ctx.getHeaders(), null);
        }
        return new EventBusPublishResult<T>(ctx.getMessage(), ctx.getTransportNames(), ctx.getHeaders(), new EventBusTransportException("Some transports failed", errors));
    }

    private <T> EventBusPublishResult<T> createFailedResult(PublishContext<T> ctx, Throwable error) {
        return new EventBusPublishResult<T>(ctx.getMessage(), ctx.getTransportNames(), ctx.getHeaders(), error);
    }

    private CompletableFuture<Collection<EventBusTransportResult>> getResultsAsync(
            List<CompletableFuture<EventBusTransportResult>> restultFutures) {
        Collection<EventBusTransportResult> syncCollection = java.util.Collections
                .synchronizedCollection(new java.util.ArrayList<>());
        List<CompletableFuture<Boolean>> collect = restultFutures
                .stream()
                .map(r -> addToCollection(syncCollection, r))
                .collect(Collectors.toList());
        CompletableFuture<Void> allAddFuture = CompletableFuture.allOf(collect.toArray(new CompletableFuture[0]));
        return allAddFuture.thenApply(dummy -> syncCollection);

    }

    private CompletableFuture<Boolean> addToCollection(
            Collection<EventBusTransportResult> syncCollection,
            CompletableFuture<EventBusTransportResult> resultFuture) {
        return resultFuture
                .thenApply(syncCollection::add)
                .exceptionally(e -> false); // TODO log this
    }

    @Override
    public <T> CompletableFuture<EventBusPublishResult<T>> publishAsync(final T message) {
        return publishAsync(message, ctx -> {
        });
    }

    private abstract class TransportTaskBase {
        protected final EventBusTransport transport;

        public TransportTaskBase(final EventBusTransport transport) {
            this.transport = transport;
        }

        public abstract CompletableFuture<EventBusTransportResult> getResultAsync();
    }

    private final class TransportTask<T> extends TransportTaskBase {
        private final PublishContext<T> context;

        public TransportTask(final EventBusTransport transport, final PublishContext<T> context) {
            super(transport);
            this.context = context;
        }

        @Override
        public CompletableFuture<EventBusTransportResult> getResultAsync() {
            try {
                return transport
                        .dispatchAsync(context)
                        .handle(this::handleError);
            } catch (final Exception e) {
                final CompletableFuture<EventBusTransportResult> result = CompletableFuture.completedFuture(
                        EventBusTransportResult.failed(transport, context, e));
                return result;
            }
        }

        private EventBusTransportResult handleError(@Nullable EventBusTransportResult result,
                @Nullable Throwable error) {
            if (result != null) {
                return result;
            }

            return EventBusTransportResult.failed(transport, context, error);
        }
    }
}
