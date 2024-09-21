package com.careri78.stores.core.services;

import org.springframework.data.repository.CrudRepository;

import com.careri78.stores.domain.Book;

public interface BooksRepository extends CrudRepository<Book, Long>{
    
}
