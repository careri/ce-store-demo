package com.careri78.stores.core.commands;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.careri78.cqrs.core.NoValue;
import com.careri78.cqrs.core.RequestHandler;
import com.careri78.stores.core.repositories.BooksRepository;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
@Component
@Scope("prototype")
public final class DeleteBookCommandHandler implements RequestHandler<DeleteBookCommand> {
    private final BooksRepository repository;

    public DeleteBookCommandHandler(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<NoValue> getAsync(DeleteBookCommand request) {
        final Long id = request.getId();
        repository.deleteById(id);
        return NoValue.asFuture();
    }
}
