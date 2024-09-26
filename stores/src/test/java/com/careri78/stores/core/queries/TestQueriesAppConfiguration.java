package com.careri78.stores.core.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.careri78.cqrs.springboot.CqrsConfiguration;
import com.careri78.repositories.BookRepositoryMap;
import com.careri78.stores.core.commands.AddBookCommandHandler;
import com.careri78.stores.core.repositories.BooksRepository;

@Configuration
@Import({ CqrsConfiguration.class })
// @ComponentScan(basePackageClasses = { RepositoryMarker.class, Book.class })
public class TestQueriesAppConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    BooksRepository getRepository() {
        return new BookRepositoryMap();
    }

    @Bean
    GetBookQueryHandler getBookQueryHandler() {
        return new GetBookQueryHandler(applicationContext.getBean(BooksRepository.class));
    }

    @Bean
    AddBookCommandHandler addBookCommandHandler() {
        return new AddBookCommandHandler(applicationContext.getBean(BooksRepository.class));
    }
}
