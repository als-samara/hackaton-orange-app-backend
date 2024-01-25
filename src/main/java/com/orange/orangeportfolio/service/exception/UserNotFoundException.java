package com.orange.orangeportfolio.service.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.model.User;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "User not found in database";
	
	public UserNotFoundException() {
		super(HttpStatus.NOT_FOUND, defaultMessage);
	}
	
	public static void ThrowIfIsEmpty(Optional<User> user) {
		if(user.isEmpty()) {
			throw new UserNotFoundException();
		}
	}
}
