package com.critter.chrono.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Schedule data is invalid.")
public class InvalidScheduleException extends RuntimeException {

    public InvalidScheduleException() {
    }

    public InvalidScheduleException(String message) {
        super(message);
    }
}
