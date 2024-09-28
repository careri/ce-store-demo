package com.careri78.stores.app.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.queries.BookQuery;
import com.careri78.stores.core.queries.BooksQuery;
import com.careri78.stores.domain.Book;

@Controller
public class BooksController {

	private final CqrsDispatcher dispatcher;

	public BooksController(final CqrsDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@GetMapping("/books/{id}")
	@Async
	public CompletableFuture<ResponseEntity<Book>> getById(@PathVariable final Long id) {
		return dispatcher.getAsync(BookQuery.FromId(id))
			.thenApplyAsync(optional -> ResponseEntity.ofNullable(optional.isPresent()
				? optional.get()
				: null));
	}

	@GetMapping("/books")
	@Async
	public CompletableFuture<ResponseEntity<Iterable<Book>>> getByTitle(@RequestParam(name = "title", required = false) final String title) {
				return dispatcher.getAsync(BooksQuery.FromTitle(title))
				.thenApplyAsync(list -> ResponseEntity.ok(list));
	}

}