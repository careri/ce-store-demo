package com.careri78.stores.cqrs;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public final class CqrsConfiguration {

    @Autowired
    BeanFactory beanFactory;

    @Bean
    CqrsRequestHandlerMetadataSet getMetadataSet() {
        return new CqrsRequestHandlerMetadataSet();
    }

    @Bean
    @Scope("Prototype")
    CqrsRequestHandlerFactory getRequestHandlerFactory() {
        return new CqrsRequestHandlerFactoryImpl(beanFactory);
    }

    @Bean
    @Scope("Prototype")
    CqrsDispatcher getDispatcher() {
        return new CqrsDispatcherImpl(beanFactory.getBean(CqrsRequestHandlerFactory.class));
    }    
}
