package com.careri78.stores.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.domain.Book;

@Repository
public interface BooksRepositoryCrud extends BooksRepository, CrudRepository<Book, Long> {

}
