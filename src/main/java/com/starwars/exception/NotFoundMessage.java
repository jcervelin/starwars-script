package com.starwars.exception;

import static com.starwars.util.SWConstants.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
public class NotFoundMessage extends Exception {
	private static final long serialVersionUID = -3332292346835265371L;

	public NotFoundMessage(Long id, String type) {
		super(String.format(MOVIE_NOT_FOUND, type, id));
	}	
}