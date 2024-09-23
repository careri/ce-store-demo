package com.careri78.cqrs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class CqrsConfiguration {

    @Autowired
    ApplicationContext beanFactory;

    @Bean
    CqrsRequestHandlerMetadataSet getMetadataSet() {
        return new CqrsRequestHandlerMetadataSet();
    }

    @Bean
    @Scope("prototype")
    CqrsRequestHandlerSet getCqrsRequestHandlerSet() {
        Map<String, ValueRequestHandlerBase> beansOfType = beanFactory.getBeansOfType(ValueRequestHandlerBase.class);
        return new CqrsRequestHandlerSet(beansOfType.values());
    }
    

    @Bean
    @Scope("prototype")
    CqrsRequestHandlerFactory getRequestHandlerFactory() {
        return new CqrsRequestHandlerFactoryImpl(beanFactory.getBean(CqrsRequestHandlerMetadataSet.class), beanFactory);
    }

    @Bean
    @Scope("prototype")
    CqrsDispatcher getDispatcher() {
        return new CqrsDispatcherImpl(beanFactory.getBean(CqrsRequestHandlerFactory.class));
    }    
}
