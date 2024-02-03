package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserInvalidPropertySizeException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "The name must contain a maximum of 80 characters";
	private static final String defaultMessagePassword = "The password must contain 8 to 30 characters";

	private UserInvalidPropertySizeException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}

	public static void ThrowIfInvalidName(String propertyValue) {
		if(propertyValue.length() > 80) {
			throw new UserInvalidPropertySizeException(defaultMessage);
		}
	}
	
	public static void ThrowIfInvalidPassword(String passwordValue) {
		if(passwordValue.length() < 8 || passwordValue.length() > 30) {
			throw new UserInvalidPropertySizeException(defaultMessagePassword);
		}
	}
}
