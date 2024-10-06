package com.careri78.stores.core.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.function.Consumer;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.BookRepositoryFixture;
import com.careri78.stores.core.commands.AddBookCommand;
import com.careri78.stores.core.queries.TestQueriesAppConfiguration;
import com.careri78.stores.domain.Book;
import com.careri78.stores.domain.OutboxEntry;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class BookCreatedTests {

	@Test
	public void shouldAddBookCreatedMessage() throws InterruptedException, ExecutionException, IOException {
		try (final BookRepositoryFixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Book> bookFuture = dispatcher
					.getAsync(AddBookCommand.FromBook(new Book("Hello World")));
			bookFuture.get();
			var queue = ctx.getOutboxQueue();
            var json = queue.poll();
			var entry = ctx.getObjectMapper().reader().readValue(json, OutboxEntry.class);
            assertEquals(BookCreated.class.getName(), entry.getName());
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