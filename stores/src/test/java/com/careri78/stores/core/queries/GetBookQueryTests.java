package com.careri78.stores.core.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Closeable;
import java.util.function.Consumer;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.domain.Book;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = {TestQueriesAppConfiguration.class})
@ActiveProfiles("test")
public final class GetBookQueryTests {
	private final class Fixture implements Closeable {

		private final AnnotationConfigApplicationContext context;

		public Fixture(final AnnotationConfigApplicationContext context) {
			this.context = context;
		}

		public AnnotationConfigApplicationContext getContext() {
			return context;
		}

		public CqrsDispatcher getDispatcher() {
			return context.getBean(CqrsDispatcher.class);
		}

		@Override
		public void close() {
			context.close();
		}

		public Book createBook(String title) {
			Book book = new Book(title);
			BooksRepository repository = getBookRepository();
			return repository.save(book);
		}

		private BooksRepository getBookRepository() {
			return context.getBean(BooksRepository.class);
		}

	}

	@Test
	public void shouldNotFindBookById() throws InterruptedException, ExecutionException {
		try (final Fixture ctx = createFixture()) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher.getAsync(GetBookQuery.FromId(100L));
			final Optional<Book> actual = bookFuture.get();
			assertFalse(actual.isPresent());
		}
	}

	@Test
	public void shouldNotFindBookByTitle() throws InterruptedException, ExecutionException {
		try (final Fixture ctx = createFixture()) {
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
		try (final Fixture ctx = createFixture(f -> f.createBook(title))) {
			final CqrsDispatcher dispatcher = ctx.getDispatcher();
			final CompletableFuture<Optional<Book>> bookFuture = dispatcher
					.getAsync(GetBookQuery.FromTitle(title.substring(3, 7)));
			final Optional<Book> actual = bookFuture.get();
			assertTrue(actual.isPresent());
			assertEquals(title, actual.get().getTitle());
		}
	}

	private Fixture createFixture() throws InterruptedException, ExecutionException {
		return createFixture(null);
	}

	private Fixture createFixture(final Consumer<Fixture> init)
			throws InterruptedException, ExecutionException {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				TestQueriesAppConfiguration.class);
		Fixture fixture = new Fixture(ctx);
		if (init != null) {
			init.accept(fixture);
		}

		return fixture;
	}

}