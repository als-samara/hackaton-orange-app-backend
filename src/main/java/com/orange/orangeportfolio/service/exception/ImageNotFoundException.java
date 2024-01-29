package com.orange.orangeportfolio.service.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.model.Image;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "Image not found in database, imageName=%s";

	public ImageNotFoundException(String imageName) {
		super(HttpStatus.NOT_FOUND, defaultMessage);
	}
	
	public static void throwsIfNull(String imageName, Optional<Image> image) {
		if(image.isEmpty()) {
			throw new ImageNotFoundException(imageName);
		}
	}
}
