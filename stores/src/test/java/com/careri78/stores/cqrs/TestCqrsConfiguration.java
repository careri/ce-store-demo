package com.careri78.stores.cqrs;

import org.springframework.context.annotation.Bean;

public class TestCqrsConfiguration {
    
    @Bean
    GetIntQueryHandler getIntQueryHandler() {
        return new GetIntQueryHandler();
    }
}
