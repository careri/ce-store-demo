package com.careri78.stores.core.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.cqrs.core.NoValue;
import com.careri78.stores.core.BookRepositoryFixture;
import com.careri78.stores.core.queries.BookTestBase;
import com.careri78.stores.domain.Book;

@ActiveProfiles("test")
/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class DeleteBookCommandTests extends BookTestBase{

	@Test
	public void shouldNotFailOnMissingId() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			Optional<Book> firstBook = ctx.getFirstBook();
			assertFalse(firstBook.isPresent());
			final CompletableFuture<NoValue> bookFuture = dispatcher.getAsync(DeleteBookCommand.FromId(100L));
			final NoValue actual = bookFuture.get();
			assertNotNull(actual);
		}
	}

	@Test
	public void shouldFindBookById() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture(f -> f.addBook("En man som heter Ove"))) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			Optional<Book> firstBook = ctx.getFirstBook();
			assertTrue(firstBook.isPresent());
			Book book = firstBook.get();
			final CompletableFuture<NoValue> bookFuture = dispatcher.getAsync(DeleteBookCommand.FromId(book.getId()));
			bookFuture.get();
			firstBook = ctx.getFirstBook();
			assertFalse(firstBook.isPresent());
		}
	}
}