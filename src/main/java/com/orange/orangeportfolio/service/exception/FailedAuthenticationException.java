package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class FailedAuthenticationException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "Invalid Credentials";
	
	
	public FailedAuthenticationException() {
		super(HttpStatus.UNAUTHORIZED, defaultMessage);
	}	
}
