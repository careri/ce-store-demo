package com.careri78.stores.core.queries;

import java.util.Optional;

import com.careri78.cqrs.core.ValueRequest;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class GetBookQuery implements ValueRequest<Optional<Book>> {
    private Long id = -1L;
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

    public static GetBookQuery FromId(Long id) {
        GetBookQuery query = new GetBookQuery();
        query.setId(id);
        return query;
    }

    public static GetBookQuery FromTitle(String title) {
        GetBookQuery query = new GetBookQuery();
        query.setTitle(title);
        return query;
    }
}
