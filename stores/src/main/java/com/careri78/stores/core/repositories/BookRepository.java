package com.careri78.stores.core.repositories;


import java.util.Optional;

import com.careri78.stores.domain.Book;

public interface BookRepository extends EntityRepository<Book, Long>{
    Iterable<Book> findAll();
    Iterable<Book> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findById(Long id);
    void deleteById(Long id);
}
