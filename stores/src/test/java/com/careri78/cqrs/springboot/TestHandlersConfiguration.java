package com.careri78.cqrs.springboot;

import org.springframework.context.annotation.Bean;

import com.careri78.cqrs.core.GetIntQueryHandler;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public class TestHandlersConfiguration {
    
    @Bean
    GetIntQueryHandler getIntQueryHandler() {
        return new GetIntQueryHandler();
    }
}
