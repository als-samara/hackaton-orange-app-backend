package com.orange.orangeportfolio.service.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.orange.orangeportfolio.model.Project;


@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ProjectAuthorizationException extends HttpClientErrorException {

    private static final long serialVersionUID = 1L;
    private static final String defaultMessage = "Você não pode alterar projetos que não são seus";

    public ProjectAuthorizationException(){
        super(HttpStatus.FORBIDDEN, defaultMessage);
    }

    public static void ThrowIfNotAuthorized(Optional<Project> result) {
        throw new ProjectAuthorizationException();
    }
}
