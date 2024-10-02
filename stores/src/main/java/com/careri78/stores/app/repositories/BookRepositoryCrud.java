package com.careri78.stores.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.domain.Book;

@Repository
public interface BookRepositoryCrud extends BookRepository, CrudRepository<Book, Long> {

}
