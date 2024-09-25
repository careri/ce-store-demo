package com.careri78.stores.core.repositories;

import java.util.List;
import java.util.Optional;

import com.careri78.stores.domain.Book;

public interface BooksRepository extends EntityRepository<Book, Long>{
    List<Book> findByTitleContainingWithIgnoreCase(String title);
    Optional<Book> findById(Long id);
}
