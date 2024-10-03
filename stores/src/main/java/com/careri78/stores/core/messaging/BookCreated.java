package com.careri78.stores.core.messaging;

import com.careri78.stores.domain.Book;

public final class BookCreated {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static BookCreated FromBook(Book createdBook) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FromBook'");
    }

}
