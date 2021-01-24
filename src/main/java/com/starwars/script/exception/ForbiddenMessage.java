package com.starwars.script.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.starwars.script.util.SWConstants.MOVIE_SCRIPT_RECEIVED;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = MOVIE_SCRIPT_RECEIVED) // 403
public class ForbiddenMessage extends RuntimeException {
	private static final long serialVersionUID = -3332292346835265371L;

	public ForbiddenMessage() {
		super(MOVIE_SCRIPT_RECEIVED);
	}

	public ForbiddenMessage(String message) {
		super(message);
	}
}