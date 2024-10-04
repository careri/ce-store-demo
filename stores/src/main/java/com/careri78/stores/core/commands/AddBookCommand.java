package com.careri78.stores.core.commands;


import com.careri78.cqrs.core.ValueRequest;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class AddBookCommand implements ValueRequest<Book> {
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public static AddBookCommand FromBook(Book book) {
        AddBookCommand query = new AddBookCommand();
        query.setBook(book);
        return query;
    }
}
