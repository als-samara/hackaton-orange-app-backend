package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserWithSameEmailAlreadyCreatedException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "An user with the same email is already created";

	public UserWithSameEmailAlreadyCreatedException() {
		super(HttpStatus.BAD_REQUEST, defaultMessage);
	}	
}
