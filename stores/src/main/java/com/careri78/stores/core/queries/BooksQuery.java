package com.careri78.stores.core.queries;

import com.careri78.cqrs.core.ValueRequest;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class BooksQuery implements ValueRequest<Iterable<Book>> {
    private String title;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public static BooksQuery FromTitle(String title) {
        BooksQuery query = new BooksQuery();
        query.setTitle(title);
        return query;
    }
}
