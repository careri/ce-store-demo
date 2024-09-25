package com.careri78.stores.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.careri78.cqrs.springboot.CqrsConfiguration;

@Configuration
@Import({ CqrsConfiguration.class })
public class AppConfiguration {
}
