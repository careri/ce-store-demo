package com.careri78.stores.app.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;

import com.careri78.stores.core.repositories.OutboxEntryRepository;
import com.careri78.stores.core.services.OutboxPublishService;
import com.careri78.stores.domain.OutboxEntry;

import jakarta.jms.Queue;
import jakarta.transaction.Transactional;

public class OutboxPublishServiceJms implements OutboxPublishService {

    private final OutboxEntryRepository repository;
    private final JmsTemplate jmsTemplate;
    private final Queue outboxQueue;
    private final Semaphore publishSemaphore = new Semaphore(1);
    private int triggerCount;

    public OutboxPublishServiceJms(
            final OutboxEntryRepository repository,
            final JmsTemplate jmsTemplate,
            final Queue outboxQueue) {
        super();
        this.repository = repository;
        this.jmsTemplate = jmsTemplate;
        this.outboxQueue = outboxQueue;
    }

    @Override
    @Async
    public CompletableFuture<Integer> publishAllAsync() {
        if (tryStartPublishing()) {
            try {
                triggerCount = 0;
                return CompletableFuture.completedFuture(publishAll());
            } catch (Exception e) {
                return CompletableFuture.completedFuture(0);
            }
            finally {
                publishSemaphore.release();

                if (triggerCount > 0) {
                    // Call this method again async
                }
            }
        }
        else {
            triggerCount++;
            return CompletableFuture.completedFuture(0);
        }
    }

    private Boolean tryStartPublishing() {
        try {
            return publishSemaphore.tryAcquire(1, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }

        return false;
    }

    private int publishAll() {
        int count = 0;
        try {
            while (true) {
                var batch = getBatch(10);
                if (batch.size() == 0) {
                    break;
                }

                for (OutboxEntry entry : batch) {
                    publish(entry);
                    count++;
                }
            }
        } catch (Exception e) {
        }

        return count;
    }


    private List<OutboxEntry> getBatch(int i) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .limit(i)
            .collect(Collectors.toList());
    }

    @Transactional
    private void publish(OutboxEntry entry) {
        jmsTemplate.convertAndSend(outboxQueue, entry);
        repository.deleteById(entry.getId());
    }

}
