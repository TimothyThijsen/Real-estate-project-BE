package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistingRequestException extends ResponseStatusException {
    public AlreadyExistingRequestException() {
        super(HttpStatus.BAD_REQUEST, "You already have a request for this property");
    }
}
