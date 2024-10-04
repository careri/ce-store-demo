package com.careri78.stores.core.commands;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.messaging.BookCreated;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.domain.Book;
import com.careri78.stores.domain.OutboxEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
@Component
@Scope("prototype")
public class AddBookCommandHandler implements ValueRequestHandler<AddBookCommand, Book> {
    private static final Logger log = LoggerFactory.getLogger(AddBookCommandHandler.class);
    private final BookRepository repository;
    private final OutboxEntryRepository outboxEntryRepository;
    private final CqrsDispatcher dispatcher;
    private final ObjectMapper mapper;

    public AddBookCommandHandler(
            final BookRepository repository,
            final OutboxEntryRepository outboxEntryRepository,
            final ObjectMapper mapper,
            final CqrsDispatcher dispatcher) {
        this.repository = repository;
        this.outboxEntryRepository = outboxEntryRepository;
        this.mapper = mapper;
        this.dispatcher = dispatcher;
    }

    @Override
    public CompletableFuture<Book> getAsync(final AddBookCommand request) {
        final Book book = request.getBook();
        log.debug("%s", request.getBook());
        if (book == null) {
            throw new IllegalArgumentException(String.format("%s book is requierd for command"));
        }

        final OutboxEntry entry = new OutboxEntry(BookCreated.class.getName(), "stores");
        return CompletableFuture
                .supplyAsync(() -> CreateBook(book, entry))
                .thenComposeAsync(createdBook -> {
                    dispatcher.sendAsync(new ProcessOutboxCommand()); // Don't wait for the result
                    log.debug("Done: %s", createdBook);
                    return CompletableFuture.completedStage(createdBook);
                });
    }

    @Transactional
    private Book CreateBook(final Book book, final OutboxEntry entry)  {
        try {
            final var createdBook = repository.save(book);
            final BookCreated bookCreated = BookCreated.FromBook(createdBook);
            final String json = mapper.writer().writeValueAsString(bookCreated);
            entry.setContent(json);
            outboxEntryRepository
                    .save(new OutboxEntry(BookCreated.class.getName(), "stores", json));
            return createdBook;
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Failed to create book", e);
        }
    }
}
