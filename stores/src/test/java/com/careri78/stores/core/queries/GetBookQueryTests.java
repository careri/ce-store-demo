package com.careri78.stores.core.queries;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.domain.Book;

public final class GetBookQueryTests {
    @Test
    public void shouldNotFindBookById() throws InterruptedException, ExecutionException {
        try (final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
			TestQueriesAppConfiguration.class)) {
			final CqrsDispatcher dispatcher = ctx.getBean(CqrsDispatcher.class);
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher.getAsync(GetBookQuery.FromId(100L));
			final Optional<Book> actual = bookFuture.get();
			assertFalse(actual.isPresent());
		}
    }

    @Test
    public void shouldNotFindBookByTitle() throws InterruptedException, ExecutionException {
        try (final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
			TestQueriesAppConfiguration.class)) {
			final CqrsDispatcher dispatcher = ctx.getBean(CqrsDispatcher.class);
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher.getAsync(GetBookQuery.FromTitle("Hello World"));
			final Optional<Book> actual = bookFuture.get();
			assertFalse(actual.isPresent());
		}
    }
}