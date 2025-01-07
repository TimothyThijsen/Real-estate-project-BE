package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TransactionException extends ResponseStatusException {
    public TransactionException() {
        super(HttpStatus.BAD_REQUEST, "Transaction failed");
    }
    public TransactionException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
