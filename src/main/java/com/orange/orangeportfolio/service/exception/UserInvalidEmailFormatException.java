package com.orange.orangeportfolio.service.exception;

import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserInvalidEmailFormatException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "The email format is not valid.";
	private static final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	public UserInvalidEmailFormatException() {
		super(HttpStatus.BAD_REQUEST, defaultMessage);
	}

	public static void throwIfInvalidEmail(String emailAddress) {
		var isValid = Pattern.compile(regexPattern)
			      .matcher(emailAddress)
			      .matches();
		
		if(!isValid) {
			throw new UserInvalidEmailFormatException();
		}
	}
}
