package com.careri78.stores.core.queries;

import com.careri78.stores.core.services.BookRepositoryImpl;
import com.careri78.stores.core.services.BooksRepository;

public final class GetBookQueryHandler implements QueryHandler<GetBookQuery> {
    private final BooksRepository repository;

    public GetBookQueryHandler(BooksRepository repository) {
        super();
        this.repository = repository;
    }
}
