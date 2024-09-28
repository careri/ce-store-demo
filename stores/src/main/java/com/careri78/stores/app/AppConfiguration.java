package com.careri78.stores.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.careri78.cqrs.springboot.CqrsConfiguration;
import com.careri78.stores.app.repositories.RepositoryMarker;
import com.careri78.stores.domain.DomainMarker;

@Configuration
@Import({ CqrsConfiguration.class })
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { RepositoryMarker.class })
@EntityScan(basePackageClasses = { DomainMarker.class })
/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
public class AppConfiguration {
}
