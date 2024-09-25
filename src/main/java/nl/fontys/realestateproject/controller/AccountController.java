package nl.fontys.realestateproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.domain.Property.CreatePropertyRequest;
import nl.fontys.realestateproject.domain.Property.CreatePropertyResponse;
import nl.fontys.realestateproject.domain.User.CreateAccountRequest;
import nl.fontys.realestateproject.domain.User.CreateAccountResponse;
import nl.fontys.realestateproject.domain.User.GetAllAccountsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<CreateAccountResponse> createProperty(@RequestBody @Valid CreateAccountRequest request) {
        CreateAccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetAllAccountsResponse> getAllAccounts() {
        GetAllAccountsResponse response = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
