package nl.fontys.realestateproject.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRefreshTokenException extends ResponseStatusException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_EXPIRED");
    }

    public InvalidRefreshTokenException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
