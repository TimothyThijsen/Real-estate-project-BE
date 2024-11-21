package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ContractNotFoundException extends ResponseStatusException {
    public ContractNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Contract not found");
    }
}
