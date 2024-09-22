package com.careri78.stores.cqrs;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.BeanFactory;

public final class CqrsRequestHandlerMetadataSet {
    private final ConcurrentMap<Class<? extends ValueRequestHandlerBase>, CqrsRequestHandlerMetadata> metadataByRequest = new ConcurrentHashMap<Class<? extends ValueRequestHandlerBase>, CqrsRequestHandlerMetadata>();

    public <TRequest extends ValueRequest<T>, T> CqrsRequestHandlerMetadata getMetadata(TRequest request,
            BeanFactory beanFactory) {
        return doGetMetadata(request.getClass(), beanFactory);
    }

    public <TRequest extends ValueRequest<T>, T> CqrsRequestHandlerMetadata getMetadata(
            Class<? extends TRequest> requestClass, BeanFactory beanFactory) {
        return doGetMetadata(requestClass, beanFactory);
    }

    private CqrsRequestHandlerMetadata doGetMetadata(Class<?> requestClass, BeanFactory beanFactory) {
        // We assume that we are in the same Dependency Injection scope for now, we need to figure that out.
        // Maybe have multiple instances of this class, store by scope.
        if (!metadataByRequest.containsKey(requestClass)) {
            addMissingHandlers(beanFactory);
        }

        CqrsRequestHandlerMetadata metadata = metadataByRequest.get(requestClass);
        if (metadata == null) {
            throw new IllegalArgumentException(String.format("%s doesn't have a registered handler", requestClass.getName()));
        }

        return metadata;
    }

    private void addMissingHandlers(BeanFactory beanFactory) {
        CqrsRequestHandlerSet set = beanFactory.getBean(CqrsRequestHandlerSet.class);
        for (ValueRequestHandlerBase handler : set.getValueHandlers()) {
            metadataByRequest.computeIfAbsent(
                    handler.getClass(),
                    cls -> CqrsRequestHandlerMetadata.createFromHandlerClass(cls));
        }

    }
}
