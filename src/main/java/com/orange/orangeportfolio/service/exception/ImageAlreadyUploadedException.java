package com.orange.orangeportfolio.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ImageAlreadyUploadedException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "Image with the same name is already uploaded";
	
	public ImageAlreadyUploadedException() {
		super(HttpStatus.BAD_REQUEST, defaultMessage);
	}
}
