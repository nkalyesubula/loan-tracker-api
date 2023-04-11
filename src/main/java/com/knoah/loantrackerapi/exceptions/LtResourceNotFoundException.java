package com.knoah.loantrackerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LtResourceNotFoundException extends RuntimeException {
    public LtResourceNotFoundException(String message) {
        super(message);
    }
}
