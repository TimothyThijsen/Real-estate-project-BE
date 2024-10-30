package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyInUse extends ResponseStatusException {
    public EmailAlreadyInUse() {
        super(HttpStatus.BAD_REQUEST, "Account with this email already exists");
    }
}
