package com.careri78.stores.core.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.careri78.cqrs.springboot.CqrsConfiguration;
import com.careri78.stores.core.repositories.BookRepositoryConfiguration;
import com.careri78.stores.core.repositories.BooksRepository;

import jakarta.persistence.EntityManager;

@Import({ CqrsConfiguration.class, BookRepositoryConfiguration.class, TestEntityManagerAutoConfiguration.class })
public class TestQueriesAppConfiguration {
    
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestEntityManager entityManager;

    @Bean
    GetBookQueryHandler getBookQueryHandler() {
        return new GetBookQueryHandler(applicationContext.getBean(BooksRepository.class));
    }

    @Bean
    EntityManager getEntityManager() {
        return entityManager.getEntityManager();
    }
}
