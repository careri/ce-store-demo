package com.careri78.stores.core.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.BookRepositoryFixture;
import com.careri78.stores.core.queries.TestQueriesAppConfiguration;
import com.careri78.stores.domain.Book;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = {TestQueriesAppConfiguration.class})
@ActiveProfiles("test")
public final class AddBookCommandsTests {
	
	@Test
	public void shouldThrowIfBookIdExists() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			Book book = ctx.addBook("Hello");
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Book> bookFuture = dispatcher.getAsync(AddBookCommand.FromBook(book));
			assertThrows(Throwable.class, bookFuture::get);
		}
	}

	@Test
	public void shouldAddBook() throws InterruptedException, ExecutionException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Book> bookFuture = dispatcher
					.getAsync(AddBookCommand.FromBook(new Book("Hello World")));
			final Book actual = bookFuture.get();
			assertNotNull(actual);
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