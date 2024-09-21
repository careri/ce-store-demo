package com.careri78.stores.core.repositories;

import org.springframework.context.annotation.Bean;

import jakarta.persistence.EntityManager;

public class BookRepositoryConfiguration {

    final EntityManager entityManager;

    public BookRepositoryConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    BooksRepository getRepository() {
        return new BookRepositoryImpl(entityManager);
    }
}
