package com.library.exceptions.CustomNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthenticationNotFoundException extends RuntimeException {
    public AuthenticationNotFoundException(String exception){
        super(exception);
    }
}
