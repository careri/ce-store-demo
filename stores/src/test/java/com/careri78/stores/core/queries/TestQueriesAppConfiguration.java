package com.careri78.stores.core.queries;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.cqrs.springboot.CqrsConfiguration;
import com.careri78.repositories.BookRepositoryMap;
import com.careri78.repositories.OutboxEntryRepositoryMap;
import com.careri78.stores.app.services.OutboxPublishServiceJms;
import com.careri78.stores.core.commands.AddBookCommandHandler;
import com.careri78.stores.core.commands.DeleteBookCommandHandler;
import com.careri78.stores.core.commands.ProcessOutboxCommandHandler;
import com.careri78.stores.core.repositories.BookRepository;
import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.core.services.OutboxPublishService;
import com.careri78.stores.domain.OutboxEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;

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
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public OutboxPublishService outboxPublishService(
            final OutboxEntryRepository repository,
            final JmsTemplate jmsTemplate,
            final Queue outboxQueue,
            final ObjectMapper objectMapper) {
        return new OutboxPublishServiceJms(repository, jmsTemplate, outboxQueue, objectMapper);
    }

    @Bean
    public java.util.Queue<Object> outboxEntryQueueData() {
        return new LinkedList<>();
    }

    @Bean
    public JmsTemplate jmsTemplate(@Qualifier("outboxEntryQueueData") java.util.Queue<Object> entryQueue)
            throws JMSException {
        var queue = mock(JmsTemplate.class);
        doAnswer(i -> entryQueue.offer(i.getArgument(1)))
                .when(queue)
                .convertAndSend(any(Destination.class), any(Object.class));
        return queue;
    }

    @Bean
    public Queue outboxQueue() throws JMSException {
        var queue = mock(Queue.class);
        when(queue.getQueueName()).thenReturn("outboxQueue");
        return queue;
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
    ProcessOutboxCommandHandler getProcessOutboxCommandHandler() {
        return new ProcessOutboxCommandHandler(applicationContext.getBean(OutboxPublishService.class));
    }

    @Bean
    AddBookCommandHandler addBookCommandHandler(
            final BookRepository bookRepository,
            final OutboxEntryRepository outboxEntryRepository,
            final CqrsDispatcher dispatcher,
            final ObjectMapper mapper) {
        return new AddBookCommandHandler(
                bookRepository,
                outboxEntryRepository,
                mapper,
                dispatcher);
    }

    @Bean
    DeleteBookCommandHandler deleteBookCommandHandlerBookCommandHandler() {
        return new DeleteBookCommandHandler(applicationContext.getBean(BookRepository.class));
    }
}
