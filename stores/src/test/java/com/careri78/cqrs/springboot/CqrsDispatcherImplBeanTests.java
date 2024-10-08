package com.careri78.cqrs.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.cqrs.core.GetIntQuery;

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
