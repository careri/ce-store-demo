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
public final class BookQuery implements ValueRequest<Optional<Book>> {
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
