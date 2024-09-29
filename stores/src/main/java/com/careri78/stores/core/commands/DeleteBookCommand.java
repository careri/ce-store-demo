package com.careri78.stores.core.commands;

import com.careri78.cqrs.core.Request;
import com.careri78.stores.core.queries.BookQuery;

public final class DeleteBookCommand implements Request {
    private Long id = -1L;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public static BookQuery FromId(Long id) {
        BookQuery query = new BookQuery();
        query.setId(id);
        return query;
    }
}
