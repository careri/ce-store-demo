package com.careri78.stores.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.careri78.stores.core.services.BookRepositoryImpl;
import com.careri78.stores.core.services.BooksRepository;

import jakarta.persistence.EntityManager;

@Configuration
public class AppConfiguration {

    final EntityManager entityManager;

    public AppConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    BooksRepository myBooksRepository() {
        return new BookRepositoryImpl(entityManager);
    }
    
}
