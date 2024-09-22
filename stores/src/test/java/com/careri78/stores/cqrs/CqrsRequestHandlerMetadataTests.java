package com.careri78.stores.cqrs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class CqrsRequestHandlerMetadataTests {

    @Test
	void shouldHaveRequestClass() {
        CqrsRequestHandlerMetadata metadata = CqrsRequestHandlerMetadata.createFromHandlerClass(GetIntQueryHandler.class);
        assertEquals(GetIntQuery.class, metadata.getQueryClass());
	}
}
