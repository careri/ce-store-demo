package com.careri78.stores.core.queries;

import java.util.concurrent.CompletableFuture;

import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.cqrs.ValueRequestHandler;
import com.careri78.stores.domain.Book;

public final class GetBookQueryHandler implements ValueRequestHandler<GetBookQuery, Book> {
    @SuppressWarnings("unused")
    private final BooksRepository repository;

    public GetBookQueryHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Book> getAsync(GetBookQuery query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAsync'");
    }
}
