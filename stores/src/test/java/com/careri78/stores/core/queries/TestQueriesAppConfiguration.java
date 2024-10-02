package com.careri78.stores.core.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.careri78.cqrs.springboot.CqrsConfiguration;
import com.careri78.repositories.BookRepositoryMap;
import com.careri78.repositories.OutboxEntryRepositoryMap;
import com.careri78.stores.core.commands.AddBookCommandHandler;
import com.careri78.stores.core.commands.DeleteBookCommandHandler;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.core.repositories.OutboxEntryRepository;

@Configuration
@Import({ CqrsConfiguration.class })
// @ComponentScan(basePackageClasses = { RepositoryMarker.class, Book.class })
/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public class TestQueriesAppConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    BookRepository getBookRepository() {
        return new BookRepositoryMap();
    }

    @Bean
    OutboxEntryRepositoryMap getOutboxRepository() {
        return new OutboxEntryRepositoryMap();
    }

    @Bean
    BookQueryHandler getBookQueryHandler() {
        return new BookQueryHandler(applicationContext.getBean(BookRepository.class));
    }

    @Bean
    BooksQueryHandler getBooksQueryHandler() {
        return new BooksQueryHandler(applicationContext.getBean(BookRepository.class));
    }

    @Bean
    OutboxEntryQueryHandler getOutboxEntryQueryHandler() {
        return new OutboxEntryQueryHandler(applicationContext.getBean(OutboxEntryRepository.class));
    }

    @Bean
    OutboxEntriesQueryHandler getOutboxEntriesQueryHandler() {
        return new OutboxEntriesQueryHandler(applicationContext.getBean(OutboxEntryRepository.class));
    }

    @Bean
    AddBookCommandHandler addBookCommandHandler() {
        return new AddBookCommandHandler(
            applicationContext.getBean(BookRepository.class),
            applicationContext.getBean(OutboxEntryRepository.class));
    }

    @Bean
    DeleteBookCommandHandler deleteBookCommandHandlerBookCommandHandler() {
        return new DeleteBookCommandHandler(applicationContext.getBean(BookRepository.class));
    }
}
