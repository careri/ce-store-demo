package com.careri78.stores.cqrs;

import org.springframework.beans.factory.BeanFactory;

public final class CqrsRequestHandlerFactoryImpl implements CqrsRequestHandlerFactory {

    @SuppressWarnings("unused")
    private final BeanFactory beanFactory;

    public CqrsRequestHandlerFactoryImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <TRequest extends ValueRequest<T>, T> ValueRequestHandler<TRequest, T> get(TRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
