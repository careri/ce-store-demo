package com.careri78.stores.app.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.queries.OutboxEntriesQuery;
import com.careri78.stores.core.queries.OutboxEntryQuery;
import com.careri78.stores.domain.OutboxEntry;

@RestController
@RequestMapping(path = "/api/outbox", produces = MediaType.APPLICATION_JSON_VALUE)
public class OutboxController {

	private final CqrsDispatcher dispatcher;

	public OutboxController(final CqrsDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@GetMapping(path = "{id}")
	@Async
	public CompletableFuture<ResponseEntity<OutboxEntry>> getByIdAsync(@PathVariable(name = "id", required = true) final Long id) {
		return dispatcher.getAsync(OutboxEntryQuery.FromId(id))
			.thenApplyAsync(optional -> ResponseEntity.ofNullable(optional.isPresent()
				? optional.get()
				: null));
	}

	@GetMapping(path = "")
	@Async
	public CompletableFuture<ResponseEntity<Iterable<OutboxEntry>>> findAsync(@RequestParam(name = "name", required = false) final String name) {
				return dispatcher.getAsync(OutboxEntriesQuery.FromName(name))
				.thenApplyAsync(list -> ResponseEntity.ok(list));
	}
}