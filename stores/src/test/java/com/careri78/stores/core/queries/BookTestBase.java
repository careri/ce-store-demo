package com.careri78.stores.core.queries;

import java.util.function.Consumer;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.careri78.stores.core.BookRepositoryFixture;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public abstract class BookTestBase {

	protected BookRepositoryFixture createFixture() throws InterruptedException, ExecutionException {
		return createFixture(null);
	}

	protected BookRepositoryFixture createFixture(final Consumer<BookRepositoryFixture> init)
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