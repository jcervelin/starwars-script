package com.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Movie Forbidden") //403
public class ForbiddenMessage extends Exception{
	private static final long serialVersionUID = -3332292346835265371L;

	public ForbiddenMessage() {
		super("Movie script already received");
	}

}