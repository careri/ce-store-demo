package com.careri78.stores.core.queries;

import com.careri78.stores.core.repositories.BookRepositoryImpl;
import com.careri78.stores.core.repositories.BooksRepository;

public final class GetBookQueryHandler implements QueryHandler<GetBookQuery> {
    private final BooksRepository repository;

    public GetBookQueryHandler(BooksRepository repository) {
        super();
        this.repository = repository;
    }
}
