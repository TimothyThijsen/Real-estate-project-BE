package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CredentialsException extends ResponseStatusException {
    public CredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid login credentials");
    }
}
