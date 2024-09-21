package com.careri78.stores.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.careri78.stores.core.repositories.BookRepositoryConfiguration;
import com.careri78.stores.cqrs.CqrsConfiguration;

@Configuration
@Import({ CqrsConfiguration.class, BookRepositoryConfiguration.class })
public class AppConfiguration {
}
