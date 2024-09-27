package com.careri78.stores.core.commands;

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
public final class AddBookCommandHandler implements ValueRequestHandler<AddBookCommand, Book> {
    private final BooksRepository repository;

    public AddBookCommandHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Book> getAsync(AddBookCommand request) {
        final Book book = request.getBook();
        if (book == null) {
            throw new IllegalArgumentException(String.format("%s book is requierd for command"));
        }

        return CompletableFuture.supplyAsync(() -> repository.save(book));
    }
}
