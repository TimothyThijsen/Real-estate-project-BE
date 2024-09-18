package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPropertyException extends ResponseStatusException {
    public InvalidPropertyException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}