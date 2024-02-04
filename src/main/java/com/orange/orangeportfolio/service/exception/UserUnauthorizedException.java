package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserUnauthorizedException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "You cannot update or delete another user.";

	public UserUnauthorizedException() {
		super(HttpStatus.UNAUTHORIZED, defaultMessage);
	}
}