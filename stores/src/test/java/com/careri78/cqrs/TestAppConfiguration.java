package com.careri78.cqrs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CqrsConfiguration.class, TestCqrsConfiguration.class })
public class TestAppConfiguration {
}
