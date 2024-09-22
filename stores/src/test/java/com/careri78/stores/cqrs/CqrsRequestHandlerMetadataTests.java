package com.careri78.stores.cqrs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class CqrsRequestHandlerMetadataTests {

    @Test
	void shouldHaveRequestClass() {
        CqrsRequestHandlerMetadata metadata = CqrsRequestHandlerMetadata.createFromHandlerClass(GetIntQueryHandler.class);
        assertEquals(GetIntQuery.class, metadata.getQueryClass());
	}

    @Test
	void shouldHaveRequestHandlerClass() {
        CqrsRequestHandlerMetadata metadata = CqrsRequestHandlerMetadata.createFromHandlerClass(GetIntQueryHandler.class);
        assertEquals(GetIntQueryHandler.class, metadata.getHandlerClass());
	}

    @Test
	void shouldHaveReturnClass() {
        CqrsRequestHandlerMetadata metadata = CqrsRequestHandlerMetadata.createFromHandlerClass(GetIntQueryHandler.class);
        assertEquals(Integer.class, metadata.getReturnClass());
	}
}
