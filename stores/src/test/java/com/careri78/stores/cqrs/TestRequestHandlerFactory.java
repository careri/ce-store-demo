package com.careri78.stores.cqrs;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TestRequestHandlerFactory implements CqrsRequestHandlerFactory {
    private final Map<Class<?>, CqrsRequestHandlerMetadata> handlerByRequest;

    @SafeVarargs
    public TestRequestHandlerFactory(Class<? extends ValueRequestHandlerBase>... handlers) {
        List<CqrsRequestHandlerMetadata> metadatas = Arrays.stream(handlers)
            .map(cls -> CqrsRequestHandlerMetadata.createFromHandlerClass(cls))
            .collect(Collectors.toList());
        handlerByRequest = metadatas.stream().collect(Collectors.toMap(
            m -> m.getQueryClass(), 
            m -> m));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TRequest extends ValueRequest<T>, T> ValueRequestHandler<TRequest, T> get(TRequest request) {
        CqrsRequestHandlerMetadata metadata = handlerByRequest.get(request.getClass());
        Class<?> handlerClass = metadata.getHandlerClass();
        try {
            Object newInstance = handlerClass.getDeclaredConstructor().newInstance();
            return (ValueRequestHandler<TRequest, T>) newInstance;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException(String.format("%s can't be created for request %s", handlerClass.getName(), request.getClass().getName()), e);
        }
    }
}
