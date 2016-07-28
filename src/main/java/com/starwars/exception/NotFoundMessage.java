package com.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Movie Not Found")
// 404
public class NotFoundMessage extends Exception {
	private static final long serialVersionUID = -3332292346835265371L;

	public NotFoundMessage(Long id, String type) {
		super("Movie " + type + " with id " + id + " not found");
	}

}