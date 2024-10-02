package com.careri78.stores.app.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careri78.cqrs.core.CqrsDispatcher;
import com.careri78.stores.core.commands.AddBookCommand;
import com.careri78.stores.core.commands.DeleteBookCommand;
import com.careri78.stores.core.queries.BookQuery;
import com.careri78.stores.core.queries.BooksQuery;
import com.careri78.stores.domain.Book;

@RestController
@RequestMapping(path = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BooksController {

	private final CqrsDispatcher dispatcher;

	public BooksController(final CqrsDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@GetMapping(path = "{id}")
	@Async
	public CompletableFuture<ResponseEntity<Book>> getByIdAsync(@PathVariable(name = "id", required = true) final Long id) {
		return dispatcher.getAsync(BookQuery.FromId(id))
			.thenApplyAsync(optional -> ResponseEntity.ofNullable(optional.isPresent()
				? optional.get()
				: null));
	}

	@GetMapping(path = "")
	@Async
	public CompletableFuture<ResponseEntity<Iterable<Book>>> findAsync(@RequestParam(name = "title", required = false) final String title) {
				return dispatcher.getAsync(BooksQuery.FromTitle(title))
				.thenApplyAsync(list -> ResponseEntity.ok(list));
	}

	@PostMapping(path = "")
	@Async
	public CompletableFuture<ResponseEntity<Book>> addAsync(@RequestBody(required = true) final Book book) {
				return dispatcher.getAsync(AddBookCommand.FromBook(book))
				.thenApplyAsync(b -> ResponseEntity.ok(b));
	}

	@DeleteMapping(path = "{id}")
	@Async
	public CompletableFuture<Object> deleteByIdAsync(@PathVariable(name = "id", required = true) final Long id) {
		return dispatcher.getAsync(DeleteBookCommand.FromId(id))
			.thenApplyAsync(noValue -> ResponseEntity.noContent().build());
	}

}