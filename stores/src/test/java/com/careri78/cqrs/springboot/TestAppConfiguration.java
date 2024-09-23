package com.careri78.cqrs.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CqrsConfiguration.class, TestHandlersConfiguration.class })
public class TestAppConfiguration {
}
