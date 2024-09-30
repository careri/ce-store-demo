package com.careri78.stores.core;

import java.io.Closeable;
import java.util.Optional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.repositories.BooksRepository;
import com.careri78.stores.domain.Book;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
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

    public Iterable<Book> getAllBooks() {
        return getBookRepository().findAll();
    }

    public Optional<Book> getFirstBook() {
        Iterable<Book> allBooks = getAllBooks();
        for (Book book : allBooks) {
            return Optional.of(book);
        }

        return Optional.empty();
    }

    private BooksRepository getBookRepository() {
        return context.getBean(BooksRepository.class);
    }

}
