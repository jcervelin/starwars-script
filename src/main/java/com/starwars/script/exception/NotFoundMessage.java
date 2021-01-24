package com.starwars.script.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.starwars.script.util.SWConstants.MOVIE_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
public class NotFoundMessage extends RuntimeException {
	private static final long serialVersionUID = -3332292346835265371L;

	public NotFoundMessage(String id, String type) {
		super(String.format(MOVIE_NOT_FOUND, type, id));
	}

	public NotFoundMessage() {
		super(MOVIE_NOT_FOUND);
	}
}