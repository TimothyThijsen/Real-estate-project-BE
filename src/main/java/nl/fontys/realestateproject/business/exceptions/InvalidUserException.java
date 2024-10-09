package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {
    public InvalidUserException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
