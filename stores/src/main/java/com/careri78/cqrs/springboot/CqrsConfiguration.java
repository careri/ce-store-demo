package com.careri78.cqrs.springboot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.careri78.cqrs.core.CqrsDefaultDispatcher;
import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.cqrs.core.CqrsRequestHandlerFactory;
import com.careri78.cqrs.core.CqrsRequestHandlerSet;
import com.careri78.cqrs.core.ValueRequestHandlerBase;

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
        return new CqrsDefaultDispatcher(beanFactory.getBean(CqrsRequestHandlerFactory.class));
    }    
}
