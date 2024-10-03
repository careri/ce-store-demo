package com.careri78.stores.core.messaging;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import jakarta.jms.Queue;

@EnableJms
@Configuration
public final class MessagingConfiguration {

    
    /**
     * The queue used for triggering the outbox publish logic
     * @return An inmemory queue
     */
    @Bean
    public Queue outboxQueue() {

        return new ActiveMQQueue("local.inmemory.outbox");
    }
}
