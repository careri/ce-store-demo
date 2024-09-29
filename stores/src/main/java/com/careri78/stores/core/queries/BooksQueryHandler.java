package com.careri78.stores.core.queries;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
@Component
@Scope("prototype")
public final class BooksQueryHandler implements ValueRequestHandler<BooksQuery, Iterable<Book>> {
    private final BooksRepository repository;

    public BooksQueryHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Iterable<Book>> getAsync(BooksQuery query) {
        final String title = query.getTitle();
        Iterable<Book> book = StringUtils.isNotBlank(title)
            ? repository.findByTitleContainingIgnoreCase(title)
            : repository.findAll();
        return CompletableFuture.completedFuture(book);
    }
}
