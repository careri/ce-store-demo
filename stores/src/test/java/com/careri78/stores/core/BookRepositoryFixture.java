package com.careri78.stores.core;

import java.io.Closeable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.domain.Book;

public final class BookRepositoryFixture implements Closeable {

    private final AnnotationConfigApplicationContext context;

    public BookRepositoryFixture(final AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public CqrsDispatcher getDispatcher() {
        return context.getBean(CqrsDispatcher.class);
    }

    @Override
    public void close() {
        context.close();
    }

    public Book addBook(String title) {
        Book book = new Book(title);
        BooksRepository repository = getBookRepository();
        return repository.save(book);
    }

    private BooksRepository getBookRepository() {
        return context.getBean(BooksRepository.class);
    }

}
