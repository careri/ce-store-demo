package com.careri78.stores.core.commands;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.careri78.cqrs.core.ValueRequestHandler;
import com.careri78.stores.core.messaging.BookCreated;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.domain.Book;
import com.careri78.stores.domain.OutboxEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.Queue;

/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
@Component
@Scope("prototype")
public class AddBookCommandHandler implements ValueRequestHandler<AddBookCommand, Book> {
    private final BookRepository repository;
    private final OutboxEntryRepository outboxEntryRepository;
    private final Queue outboxQueue;
    private final JmsTemplate jms;
    private final ObjectMapper mapper;

    public AddBookCommandHandler(
            final BookRepository repository,
            final OutboxEntryRepository outboxEntryRepository,
            final JmsTemplate jms,
            @Qualifier("outboxQueue") final Queue outboxQueue,
            final ObjectMapper mapper) {
        this.repository = repository;
        this.outboxEntryRepository = outboxEntryRepository;
        this.jms = jms;
        this.outboxQueue = outboxQueue;
        this.mapper = mapper;
    }

    @Override
    public CompletableFuture<Book> getAsync(final AddBookCommand request) {
        final Book book = request.getBook();
        if (book == null) {
            throw new IllegalArgumentException(String.format("%s book is requierd for command"));
        }

        final OutboxEntry entry = new OutboxEntry(BookCreated.class.getName(), "stores");
        return CompletableFuture
                .supplyAsync(() -> CreateBook(book, entry))
                .thenComposeAsync(createdBook -> {
                    jms.convertAndSend(this.outboxQueue, entry);
                    return CompletableFuture.completedStage(createdBook);
                });
    }

    @Transactional
    private Book CreateBook(final Book book, final OutboxEntry entry)  {
        try {
            final var createdBook = repository.save(book);
            final BookCreated bookCreated = BookCreated.FromBook(createdBook);
            String json = mapper.writer().writeValueAsString(bookCreated);
            entry.setContent(json);
            outboxEntryRepository
                    .save(new OutboxEntry("book_created", "stores", json));
            return createdBook;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create book", e);
        }
    }
}
