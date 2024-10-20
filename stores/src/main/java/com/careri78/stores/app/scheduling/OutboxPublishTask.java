package com.careri78.stores.app.scheduling;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.careri78.stores.core.services.OutboxPublishService;

import java.time.Duration;

/**
 * If for some reason the outbox publish
 * isn't triggered at once on a new event. 
 * This scheduled task runs at a dynamically configured interval.
 * 
 * @author Carl Ericsson
 * 
 */
@Component
public final class OutboxPublishTask {
    private static final Logger log = LoggerFactory.getLogger(OutboxPublishTask.class);
    private final OutboxPublishService service;
    int sendSchedule;

    public OutboxPublishTask(
        final OutboxPublishService service,
        final TaskScheduler taskScheduler,
        @Value("${outbox.send.schedule.seconds}") final int sendSchedule) {
        super();
        this.service = service;
        this.sendSchedule = sendSchedule;
        taskScheduler.scheduleWithFixedDelay(() -> triggerSend(), Duration.ofSeconds(sendSchedule));
    }

	public void triggerSend() {
		try {
            log.debug("Trigger publish all");
            service.publishAllAsync().get();
        } catch (InterruptedException | ExecutionException e) {
        }
	}
}
