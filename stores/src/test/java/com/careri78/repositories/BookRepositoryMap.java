package com.careri78.repositories;

import java.util.List;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.domain.Book;

import org.apache.commons.lang3.StringUtils;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class BookRepositoryMap extends LongRepositoryMapBase<Book> implements BookRepository {

    public BookRepositoryMap() {
        super(Book.class);
    }

    @Override
    public List<Book> findByTitleContainingIgnoreCase(String title) {
        return this.findItems(book -> StringUtils.containsIgnoreCase(book.getTitle(), title));
    }

    @Override
    protected Long getId(Book entity) {
        return entity.getId();
    }

    @Override
    protected void setId(long id, Book entity) {
        entity.setId(id);
    }
}
