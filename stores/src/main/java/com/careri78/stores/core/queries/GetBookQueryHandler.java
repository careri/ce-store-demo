package com.careri78.stores.core.queries;

import java.util.List;
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
public final class GetBookQueryHandler implements ValueRequestHandler<GetBookQuery, Optional<Book>> {
    private final BooksRepository repository;

    public GetBookQueryHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Optional<Book>> getAsync(GetBookQuery query) {
        final Long id = query.getId();
        final String title = query.getTitle();
        Optional<Book> book = id > -1
            ? repository.findById(id)
            : !title.isBlank()
                ? findSingleByTitle(title)
                : Optional.empty();
        return CompletableFuture.completedFuture(book);
    }

    private Optional<Book> findSingleByTitle(String title) {
        List<Book> books = repository.findByTitleContainingIgnoreCase(title);
        return books.size() == 1
            ? Optional.of(books.getFirst())
            : Optional.empty();
    }
}
