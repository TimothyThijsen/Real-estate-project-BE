package nl.fontys.realestateproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.business.RefreshTokenService;
import nl.fontys.realestateproject.business.dto.user.*;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.configuration.security.token.AccessToken;
import nl.fontys.realestateproject.persistence.entity.RefreshTokenEntity;
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
    private final RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;
    private final RefreshTokenService refreshTokenService;


    @PostMapping()
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        CreateAccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        String accessToken = accountService.login(request);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        LoginResponse response = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping()
    public ResponseEntity<Void> updateAccount(@RequestBody @Valid UpdateAccountRequest request) {
        AccessToken accessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();
        if (accessToken.getUserId() != request.getId() && !accessToken.getRoles().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this account");
        }
        accountService.updateAccount(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int accountId) {
        AccessToken accessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();
        if (accessToken.getUserId() != accountId && !accessToken.getRoles().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this account");
        }
        accountService.deleteAccount(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
