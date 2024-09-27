package com.careri78.cqrs.springboot;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import com.careri78.cqrs.core.CqrsRequestHandlerFactory;
import com.careri78.cqrs.core.CqrsRequestHandlerMetadata;
import com.careri78.cqrs.core.ValueRequest;
import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.cqrs.core.ValueRequestHandlerBase;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class CqrsRequestHandlerFactoryImpl implements CqrsRequestHandlerFactory {

    private final BeanFactory beanFactory;
    private final CqrsRequestHandlerMetadataSet metadataSet;

    public CqrsRequestHandlerFactoryImpl(CqrsRequestHandlerMetadataSet metadataSet, BeanFactory beanFactory) {
        this.metadataSet = metadataSet;
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TRequest extends ValueRequest<T>, T> ValueRequestHandler<TRequest, T> get(TRequest request) 
        throws IllegalArgumentException, BeansException {
        final CqrsRequestHandlerMetadata metadata = metadataSet.getMetadata(request, beanFactory);
        if (metadata != null) {
            Class<? extends ValueRequestHandlerBase> handlerClass = metadata.getHandlerClass();
            Object bean = beanFactory.getBean(handlerClass);
            return (ValueRequestHandler<TRequest, T>) bean;
        }

        throw new IllegalArgumentException(String.format("%s not handler available.", request.getClass().getName()));
    }

}
