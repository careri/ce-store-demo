package com.careri78.cqrs;

import org.springframework.context.annotation.Bean;

public class TestCqrsConfiguration {
    
    @Bean
    GetIntQueryHandler getIntQueryHandler() {
        return new GetIntQueryHandler();
    }
}
