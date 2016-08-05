package com.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.starwars.util.SWConstants.*;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason=UNEXPECTED_ERROR) //400
public class DefaultErrorMessage extends Exception{
	private static final long serialVersionUID = -3332292346835265372L;

	public DefaultErrorMessage() {
		super(UNEXPECTED_ERROR);
	}
}