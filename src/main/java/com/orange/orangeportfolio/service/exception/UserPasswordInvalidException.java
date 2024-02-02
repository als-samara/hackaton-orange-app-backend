package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserPasswordInvalidException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "The password must contain at least 8 to 30 characters";

	public UserPasswordInvalidException() {
		super(HttpStatus.BAD_REQUEST, defaultMessage);
	}
	
	public static void ThrowIfInvalidPassword(String passwordValue) {
		if(passwordValue.length() < 8 || passwordValue.length() > 30) {
			throw new UserPasswordInvalidException();
		}
	}
}
