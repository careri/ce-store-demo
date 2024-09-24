package com.careri78.stores.core.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.careri78.stores.domain.Book;

@Repository
public interface BooksRepository extends CrudRepository<Book, Long>{
    List<Book> findByTitleContainingWithIgnoreCase(String title);
}
