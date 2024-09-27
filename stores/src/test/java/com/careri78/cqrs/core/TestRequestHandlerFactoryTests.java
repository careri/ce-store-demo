package com.careri78.cqrs.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public class TestRequestHandlerFactoryTests {

    @Test
	void shouldCreateHandler() {
        TestRequestHandlerFactory factory = new TestRequestHandlerFactory(GetIntQueryHandler.class);
        ValueRequestHandler<GetIntQuery, Integer> handler = factory.get(new GetIntQuery(5));
        assertNotNull(handler);
	}
}
