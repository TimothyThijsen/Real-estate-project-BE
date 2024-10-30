package nl.fontys.realestateproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.business.dto.user.*;
import nl.fontys.realestateproject.business.exceptions.CredentialsException;
import nl.fontys.realestateproject.business.exceptions.EmailAlreadyInUse;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<CreateAccountResponse> createProperty(@RequestBody @Valid CreateAccountRequest request) {
        CreateAccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler({EmailAlreadyInUse.class, InvalidUserException.class, CredentialsException.class})
    public ResponseEntity<String> handleExceptions(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<GetAllAccountsResponse> getAllAccounts() {
        GetAllAccountsResponse response = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<GetUserAccountResponse> getAccount(@PathVariable int accountId) {
        GetUserAccountResponse response = accountService.getAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<GetUserAccountResponse> login(@RequestBody @Valid LoginRequest request) {
        GetUserAccountResponse response = accountService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping()
    public ResponseEntity<Void> updateAccount(@RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateAccount(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
