package com.careri78.stores.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.careri78.stores.core.services.BooksRepository;
import com.careri78.stores.inmemory.services.BookRepositoryImpl;

import jakarta.persistence.EntityManager;

@Configuration
public class AppConfiguration {

    final EntityManager entityManager;

    public AppConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public BooksRepository myBooksRepository() {
        return new BookRepositoryImpl(entityManager);
    }
    
}
