package com.careri78.stores.cqrs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// @SpringBootTest()
class CqrsDispatcherImplBeanTests {

	@Test
	void shouldExecuteGetIntQueryAsync() throws InterruptedException, ExecutionException {
		try (final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
			TestAppConfiguration.class)) {
			final CqrsDispatcher dispatcher = ctx.getBean(CqrsDispatcher.class);
			final int expected = 10;
			final CompletableFuture<Integer> intFuture = dispatcher.getAsync(new GetIntQuery(expected));
			final Integer actual = intFuture.get();
			assertEquals(expected, actual);
		}
	}

}
