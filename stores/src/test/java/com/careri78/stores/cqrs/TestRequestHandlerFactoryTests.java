package com.careri78.stores.cqrs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TestRequestHandlerFactoryTests {

    @Test
	void shouldCreateHandler() {
        TestRequestHandlerFactory factory = new TestRequestHandlerFactory(GetIntQueryHandler.class);
        ValueRequestHandler<GetIntQuery, Integer> handler = factory.get(new GetIntQuery(5));
        assertNotNull(handler);
	}
}
