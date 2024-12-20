package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.RefreshTokenService;
import nl.fontys.realestateproject.business.exceptions.InvalidRefreshTokenException;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import nl.fontys.realestateproject.persistence.RefreshTokenRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.RefreshTokenEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    RefreshTokenService refreshTokenService;

    @Test
    void createRefreshToken_ShouldCreateRefreshToken() {
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(AccountEntity.builder().build()));
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(RefreshTokenEntity.builder().build());
        assertDoesNotThrow(() -> refreshTokenService.createRefreshToken("test@mail.com"));
        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));

    }
    @Test
    void createRefreshToken_ShouldThrowInvalidUserException_WhenUserNotFound() {
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());
        assertThrows(InvalidUserException.class, () -> refreshTokenService.createRefreshToken("test@mail.com"));
    }

    @Test
    void findByToken_ShouldFindToken() {
        when(refreshTokenRepository.findByToken("test")).thenReturn(Optional.of(RefreshTokenEntity.builder().build()));
        assertDoesNotThrow(() -> refreshTokenService.findByToken("test"));
        verify(refreshTokenRepository).findByToken("test");
    }

    @Test
    void verifyExpiration_ShouldVerifyExpiration() {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().expiryDate(Instant.now().plusSeconds(100)).build();
        assertDoesNotThrow(() -> refreshTokenService.verifyExpiration(refreshTokenEntity));
    }
    @Test
    void verifyExpiration_ShouldThrowInvalidRefreshTokenException_WhenTokenExpired() {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().expiryDate(Instant.now().minusSeconds(5)).build();
        assertThrows(InvalidRefreshTokenException.class, () -> refreshTokenService.verifyExpiration(refreshTokenEntity));
    }
    @Test
    void updateRefreshToken_ShouldUpdateToken() {
        when(refreshTokenRepository.findByToken("test")).thenReturn(Optional.of(RefreshTokenEntity.builder().build()));
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(RefreshTokenEntity.builder().build());
        assertDoesNotThrow(() -> refreshTokenService.updateRefreshToken("test"));
        verify(refreshTokenRepository).findByToken("test");
        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));
    }

    @Test
    void updateRefreshToken_ShouldThrowInvalidUserException_WhenTokenNotFound() {
        when(refreshTokenRepository.findByToken("test")).thenReturn(Optional.empty());
        assertThrows(InvalidRefreshTokenException.class, () -> refreshTokenService.updateRefreshToken("test"));
    }
}
