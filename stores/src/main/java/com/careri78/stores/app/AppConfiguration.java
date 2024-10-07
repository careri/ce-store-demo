package com.careri78.stores.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.careri78.cqrs.springboot.CqrsConfiguration;
import com.careri78.stores.app.controllers.ControllersMarker;
import com.careri78.stores.app.repositories.RepositoryMarker;
import com.careri78.stores.app.scheduling.SchedulingMarker;
import com.careri78.stores.app.services.ServiceMarker;
import com.careri78.stores.core.commands.CommandsMarker;
import com.careri78.stores.core.messaging.MessagingConfiguration;
import com.careri78.stores.core.messaging.MessagingMarker;
import com.careri78.stores.core.queries.QueriesMarker;
import com.careri78.stores.domain.DomainMarker;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Import({ CqrsConfiguration.class, MessagingConfiguration.class })
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories(basePackageClasses = { RepositoryMarker.class })
@ComponentScan(basePackageClasses = {
        ControllersMarker.class,
        QueriesMarker.class,
        CommandsMarker.class,
        MessagingMarker.class,
        ServiceMarker.class,
        SchedulingMarker.class })
@EntityScan(basePackageClasses = { DomainMarker.class })
@EnableScheduling
// @EnableWebMvc
/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
public class AppConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_jmsType");

        // // Define the types to convert
        // Map<String, Class<?>> typeIdMap = new HashMap<>();
        // typeIdMap.put("BookCreated", BookCreated.class);
        // converter.setTypeIdMappings(typeIdMap);
        return converter;
    }
}