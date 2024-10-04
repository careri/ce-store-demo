package com.careri78.stores.app.scheduling;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.careri78.stores.core.services.OutboxPublishService;

@Component
public final class OutboxPublishTask {
 
    private static final Logger log = LoggerFactory.getLogger(OutboxPublishTask.class);
    private final OutboxPublishService service;

    public OutboxPublishTask(final OutboxPublishService service) {
        super();
        this.service = service;
    }

	@Scheduled(fixedDelay = 5000)
	public void reportCurrentTime() {
		try {
            log.debug("Trigger publish all");
            service.publishAllAsync().get();
        } catch (InterruptedException | ExecutionException e) {
        }
	}
}
