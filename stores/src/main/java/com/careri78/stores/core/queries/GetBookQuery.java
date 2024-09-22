package com.careri78.stores.core.queries;

import com.careri78.stores.cqrs.ValueRequest;
import com.careri78.stores.domain.Book;

public final class GetBookQuery implements ValueRequest<Book> {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
