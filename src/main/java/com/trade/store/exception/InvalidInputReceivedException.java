package com.trade.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputReceivedException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidInputReceivedException(String message) {
		super(message);
	}

	public InvalidInputReceivedException(String message, Throwable t) {
		super(message, t);
	}

}
