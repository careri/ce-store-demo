package com.careri78.stores.cqrs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public final class CqrsConfiguration {

    @Bean
    @Scope("Request")
    CqrsDispatcher getCqrsDispatcher() {
        return new CqrsDispatcherImpl();
    }    
}
