package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.exceptions.InvalidRefreshTokenException;
import nl.fontys.realestateproject.persistence.RefreshTokenRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.RefreshTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshTokenEntity createRefreshToken(String email) {
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .account(userRepository.findByEmail(email).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
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
        RefreshTokenEntity oldToken = refreshTokenRepository.findByToken(token).get();
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
