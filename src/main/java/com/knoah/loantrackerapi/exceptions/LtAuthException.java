package com.knoah.loantrackerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LtAuthException extends RuntimeException {
    public LtAuthException(String message) {
        super(message);
    }
}
