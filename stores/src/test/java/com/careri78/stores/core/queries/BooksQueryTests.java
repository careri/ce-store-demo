package com.careri78.stores.core.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.BookRepositoryFixture;
import com.careri78.stores.domain.Book;

/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
public final class BooksQueryTests extends BookTestBase {

	@Test
	public void shouldNotFindBookByTitle() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Iterable<Book>> bookFuture = dispatcher
					.getAsync(BooksQuery.FromTitle("Hello World"));
			final Iterator<Book> actual = bookFuture.get().iterator();
			assertFalse(actual.hasNext());
		}
	}

	@Test
	public void shouldFindBookByTitle() throws InterruptedException, ExecutionException {
		String title = "My best book";
		try (final BookRepositoryFixture ctx = createFixture(f -> f.addBook(title))) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Iterable<Book>> bookFuture = dispatcher
					.getAsync(BooksQuery.FromTitle(title.substring(3, 7)));
			final Iterator<Book> actual = bookFuture.get().iterator();
			assertTrue(actual.hasNext());
			assertEquals(title, actual.next().getTitle());
		}
	}
}