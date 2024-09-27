package com.careri78.stores.core.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Consumer;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.BookRepositoryFixture;
import com.careri78.stores.domain.Book;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = {TestQueriesAppConfiguration.class})
@ActiveProfiles("test")
/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class GetBookQueryTests {

	@Test
	public void shouldNotFindBookById() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher.getAsync(GetBookQuery.FromId(100L));
			final Optional<Book> actual = bookFuture.get();
			assertFalse(actual.isPresent());
		}
	}

	@Test
	public void shouldNotFindBookByTitle() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher
					.getAsync(GetBookQuery.FromTitle("Hello World"));
			final Optional<Book> actual = bookFuture.get();
			assertFalse(actual.isPresent());
		}
	}

	@Test
	public void shouldFindBookByTitle() throws InterruptedException, ExecutionException {
		String title = "My best book";
		try (final BookRepositoryFixture ctx = createFixture(f -> f.addBook(title))) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher
					.getAsync(GetBookQuery.FromTitle(title.substring(3, 7)));
			final Optional<Book> actual = bookFuture.get();
			assertTrue(actual.isPresent());
			assertEquals(title, actual.get().getTitle());
		}
	}

	private BookRepositoryFixture createFixture() throws InterruptedException, ExecutionException {
		return createFixture(null);
	}

	private BookRepositoryFixture createFixture(final Consumer<BookRepositoryFixture> init)
			throws InterruptedException, ExecutionException {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				TestQueriesAppConfiguration.class);
		BookRepositoryFixture fixture = new BookRepositoryFixture(ctx);
		if (init != null) {
			init.accept(fixture);
		}

		return fixture;
	}
}