package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPropertyException extends ResponseStatusException {
    public InvalidPropertyException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}