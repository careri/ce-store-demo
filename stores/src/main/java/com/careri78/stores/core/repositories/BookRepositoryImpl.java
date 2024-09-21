package com.careri78.stores.core.repositories;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import com.careri78.stores.domain.Book;

import jakarta.persistence.EntityManager;

@Service
public final class BookRepositoryImpl extends SimpleJpaRepository<Book, Long> implements BooksRepository {

    public BookRepositoryImpl(EntityManager entityManager) {
        super(Book.class, entityManager);
    }

}
