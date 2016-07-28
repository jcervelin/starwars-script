package com.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Movie Unexpected Error") //400
public class DefaultErrorMessage extends Exception{
	private static final long serialVersionUID = -3332292346835265372L;

	public DefaultErrorMessage() {
		super("Unexpected Error");
	}

}