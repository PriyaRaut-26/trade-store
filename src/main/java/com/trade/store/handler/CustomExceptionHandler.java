package com.trade.store.handler;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.trade.store.exception.InvalidInputReceivedException;
import com.trade.store.exception.RecordNotFoundException;
import com.trade.store.model.ErrorResponse;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> recordNotFoundException(RecordNotFoundException exception, WebRequest request){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setErrorCode("101");
		errorResponse.setErrorMessage(exception.getMessage());
		errorResponse.setErrorCause("NA");
		errorResponse.setResponseStatusCode("404");
        return new ResponseEntity<ErrorResponse>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidInputReceivedException.class)
	public ResponseEntity<ErrorResponse> invalidInputException(InvalidInputReceivedException exception, WebRequest request){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setErrorCode("102");
		errorResponse.setErrorMessage(exception.getMessage());
		errorResponse.setErrorCause("NA");
		errorResponse.setResponseStatusCode("400");
        return new ResponseEntity<ErrorResponse>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
