package com.doxa.oauth2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AuthorizationApiException extends RuntimeException {
    public AuthorizationApiException(String message){super(message);}
}
