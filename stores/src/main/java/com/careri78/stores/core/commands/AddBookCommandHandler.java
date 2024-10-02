package com.careri78.stores.core.commands;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.domain.Book;
import com.careri78.stores.domain.OutboxEntry;

/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
@Component
@Scope("prototype")
public final class AddBookCommandHandler implements ValueRequestHandler<AddBookCommand, Book> {
    private final BookRepository repository;
    private final OutboxEntryRepository outboxEntryRepository;

    public AddBookCommandHandler(BookRepository repository, OutboxEntryRepository outboxEntryRepository) {
        this.repository = repository;
        this.outboxEntryRepository = outboxEntryRepository;
    }

    @Override
    public CompletableFuture<Book> getAsync(AddBookCommand request) {
        final Book book = request.getBook();
        if (book == null) {
            throw new IllegalArgumentException(String.format("%s book is requierd for command"));
        }

        return CompletableFuture.supplyAsync(() -> CreateBook(book));
    }

    @Transactional
    private Book CreateBook(final Book book) {
        var createdBook = repository.save(book);
        outboxEntryRepository.save(new OutboxEntry("book_created", "stores", String.format("{ \"id\": %s}", createdBook.getId())));
        return createdBook;
    }
}
