package com.knoah.loantrackerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LtBadRequestException extends RuntimeException {
    public LtBadRequestException(String message) {
        super(message);
    }
}
