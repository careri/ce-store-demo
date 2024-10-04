package com.careri78.stores.core.queries;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
@Component
@Scope("prototype")
public final class BookQueryHandler implements ValueRequestHandler<BookQuery, Optional<Book>> {
    private static final Logger log = LoggerFactory.getLogger(BookQueryHandler.class);
    private final BookRepository repository;

    public BookQueryHandler(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Optional<Book>> getAsync(BookQuery query) {
        final Long id = query.getId();
        log.debug("%s", id);
        Optional<Book> book = id > -1
            ? repository.findById(id)
            : Optional.empty();
        return CompletableFuture.completedFuture(book);
    }
}
