package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.exceptions.InvalidRefreshTokenException;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import nl.fontys.realestateproject.persistence.RefreshTokenRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;
    public RefreshTokenEntity createRefreshToken(String email) {
        Optional<AccountEntity> account = userRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new InvalidUserException();
        }
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .account(account.get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(120, ChronoUnit.MINUTES))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshTokenEntity> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new InvalidRefreshTokenException();
        }
        return token;
    }

    public RefreshTokenEntity updateRefreshToken(String token){
        Optional<RefreshTokenEntity> tokenEntity = refreshTokenRepository.findByToken(token);
        if (tokenEntity.isEmpty()) {
            throw new InvalidUserException();
        }
        RefreshTokenEntity oldToken = tokenEntity.get();
        RefreshTokenEntity updatedToken = RefreshTokenEntity.builder()
                .account(oldToken.getAccount())
                .token(UUID.randomUUID().toString())
                .expiryDate(oldToken.getExpiryDate())
                .build();
        oldToken.setExpiryDate(Instant.now());
        refreshTokenRepository.delete(oldToken);
        return refreshTokenRepository.save(updatedToken);
    }
}
