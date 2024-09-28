package com.careri78.stores.core.queries;

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
public final class BooksQueryHandler implements ValueRequestHandler<BooksQuery, Iterable<Book>> {
    private final BooksRepository repository;

    public BooksQueryHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Iterable<Book>> getAsync(BooksQuery query) {
        final String title = query.getTitle();
        Iterable<Book> book = !title.isBlank()
            ? repository.findByTitleContainingIgnoreCase(title)
            : repository.findAll();
        return CompletableFuture.completedFuture(book);
    }
}
