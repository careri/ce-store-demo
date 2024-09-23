package com.careri78.cqrs.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

class CqrsDispatcherImplTests {

	@Test
	void shouldExecuteGetIntQueryAsync() throws InterruptedException, ExecutionException {
		final int expected = 10;
		CqrsDefaultDispatcher dispatcher = new CqrsDefaultDispatcher(new TestRequestHandlerFactory(GetIntQueryHandler.class));
		CompletableFuture<Integer> intFuture = dispatcher.getAsync(new GetIntQuery(expected));
		Integer actual = intFuture.get();
		assertEquals(expected, actual);
	}

}
