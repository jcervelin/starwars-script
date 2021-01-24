package com.starwars.script.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.starwars.script.util.SWConstants.UNEXPECTED_ERROR;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason=UNEXPECTED_ERROR) //400
public class DefaultErrorMessage extends RuntimeException{
	private static final long serialVersionUID = -3332292346835265372L;

	public DefaultErrorMessage() {
		super(UNEXPECTED_ERROR);
	}
}