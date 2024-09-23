package com.careri78.cqrs.springboot;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.BeanFactory;

import com.careri78.cqrs.core.CqrsRequestHandlerMetadata;
import com.careri78.cqrs.core.CqrsRequestHandlerSet;
import com.careri78.cqrs.core.ValueRequest;
import com.careri78.cqrs.core.ValueRequestHandlerBase;

public final class CqrsRequestHandlerMetadataSet {
    private final ConcurrentMap<Class<? extends ValueRequestHandlerBase>, CqrsRequestHandlerMetadata> metadataByRequestHandler = new ConcurrentHashMap<Class<? extends ValueRequestHandlerBase>, CqrsRequestHandlerMetadata>();
    private final ConcurrentMap<Class<?>, CqrsRequestHandlerMetadata> metadataByRequest = new ConcurrentHashMap<Class<?>, CqrsRequestHandlerMetadata>();

    public <TRequest extends ValueRequest<T>, T> CqrsRequestHandlerMetadata getMetadata(TRequest request,
            BeanFactory beanFactory) {
        return doGetMetadata(request.getClass(), beanFactory);
    }

    public <TRequest extends ValueRequest<T>, T> CqrsRequestHandlerMetadata getMetadata(
            Class<? extends TRequest> requestClass, BeanFactory beanFactory) {
        return doGetMetadata(requestClass, beanFactory);
    }

    private CqrsRequestHandlerMetadata doGetMetadata(Class<?> requestClass, BeanFactory beanFactory)
        throws IllegalArgumentException {
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
            metadataByRequestHandler.computeIfAbsent(
                    handler.getClass(),
                    cls -> createAndRegisterHandler(cls));
        }

    }

    private CqrsRequestHandlerMetadata createAndRegisterHandler(Class<? extends ValueRequestHandlerBase> cls) {
        CqrsRequestHandlerMetadata handler = CqrsRequestHandlerMetadata.createFromHandlerClass(cls);
        metadataByRequest.computeIfAbsent(handler.getQueryClass(), queryCls -> handler);
        return handler;
    }
}
