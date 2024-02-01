package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProjectInvalidPropertyException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "Project property is null or empty, PropertyName=%s";
	
	
	public ProjectInvalidPropertyException(String propertyName) {
		super(HttpStatus.BAD_REQUEST, defaultMessage);
	}

	public static void ThrowIfIsNullOrEmpty(String propertyName, String propertyValue) {
		if(!StringUtils.hasText(propertyValue)) {
			throw new ProjectInvalidPropertyException(propertyName);
		}
	}
}
