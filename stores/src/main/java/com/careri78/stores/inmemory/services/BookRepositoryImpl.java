package com.careri78.stores.inmemory.services;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import com.careri78.stores.core.services.BooksRepository;
import com.careri78.stores.domain.Book;

import jakarta.persistence.EntityManager;

@Service
public class BookRepositoryImpl extends SimpleJpaRepository<Book, Long> implements BooksRepository {

    public BookRepositoryImpl(EntityManager entityManager) {
        super(Book.class, entityManager);
    }

}
