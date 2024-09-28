package com.careri78.stores.core.queries;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class BookQueryHandler implements ValueRequestHandler<BookQuery, Optional<Book>> {
    private final BooksRepository repository;

    public BookQueryHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Optional<Book>> getAsync(BookQuery query) {
        final Long id = query.getId();
        Optional<Book> book = id > -1
            ? repository.findById(id)
            : Optional.empty();
        return CompletableFuture.completedFuture(book);
    }
}
