package com.trade.store.model;

import java.util.Date;

public class ErrorResponse {

	private Date timestamp;
	private String errorCode;
	private String errorMessage;
	private Object errorCause;
	private String responseStatusCode;
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Object getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(Object errorCause) {
		this.errorCause = errorCause;
	}
	public String getResponseStatusCode() {
		return responseStatusCode;
	}
	public void setResponseStatusCode(String responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	
}
