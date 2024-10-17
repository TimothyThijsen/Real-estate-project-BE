package nl.fontys.realestateproject.business.exceptions;

public class CredentialsException extends RuntimeException {
    public CredentialsException() {
        super("Invalid login credentials");
    }
}
