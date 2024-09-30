package com.careri78.stores.core.commands;

import com.careri78.cqrs.core.Request;

public final class DeleteBookCommand implements Request {
    private Long id = -1L;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public static DeleteBookCommand FromId(Long id) {
        DeleteBookCommand query = new DeleteBookCommand();
        query.setId(id);
        return query;
    }
}
