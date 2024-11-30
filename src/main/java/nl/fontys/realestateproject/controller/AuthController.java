package nl.fontys.realestateproject.controller;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.business.RefreshTokenService;
import nl.fontys.realestateproject.business.dto.RefreshTokenRequest;
import nl.fontys.realestateproject.business.dto.user.LoginResponse;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.RefreshTokenEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class AuthController {
    RefreshTokenService refreshTokenService;
    AccountService accountService;
    @PostMapping("/refreshToken")
    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest request){
        return refreshTokenService.findByToken(request.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getAccount)
                .map(accountInfo -> {
                    String accessToken = accountService.generateAccessToken(accountInfo);
                    return LoginResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(request.getToken())
                            .build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
