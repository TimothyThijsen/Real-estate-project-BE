package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPropertyException extends ResponseStatusException {
    public InvalidPropertyException() {
        super(HttpStatus.NOT_FOUND, "PROPERTY_NOT_FOUND");
    }

    public InvalidPropertyException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}