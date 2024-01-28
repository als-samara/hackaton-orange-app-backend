package com.orange.orangeportfolio.service.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.model.Project;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends HttpClientErrorException{

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "Project not found in database";

	public ProjectNotFoundException() {
		super(HttpStatus.NOT_FOUND, defaultMessage);
	}

	public static void ThrowIfIsEmpty(Optional<Project> project) {
		if(project.isEmpty()) {
			throw new ProjectNotFoundException();
		}
	}
}
