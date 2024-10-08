package com.careri78.stores.core.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.BookRepositoryFixture;
import com.careri78.stores.domain.Book;

@ActiveProfiles("test")
/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class BookQueryTests extends BookTestBase{

	@Test
	public void shouldNotFindBookById() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			Optional<Book> firstBook = ctx.getFirstBook();
			assertFalse(firstBook.isPresent());
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher.getAsync(BookQuery.FromId(100L));
			final Optional<Book> actual = bookFuture.get();
			assertFalse(actual.isPresent());
		}
	}

	@Test
	public void shouldFindBookById() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture(f -> f.addBook("En man som heter Ove"))) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			Optional<Book> firstBook = ctx.getFirstBook();
			assertTrue(firstBook.isPresent());
			Book book = firstBook.get();
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher.getAsync(BookQuery.FromId(book.getId()));
			final Optional<Book> actual = bookFuture.get();
			assertTrue(actual.isPresent());
			assertEquals(book.getId(), actual.get().getId());
		}
	}
}