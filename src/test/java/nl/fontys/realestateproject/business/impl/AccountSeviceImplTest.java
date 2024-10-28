package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.DTO.User.CreateAccountRequest;
import nl.fontys.realestateproject.business.DTO.User.LoginRequest;
import nl.fontys.realestateproject.business.DTO.User.UpdateAccountRequest;
import nl.fontys.realestateproject.business.exceptions.CredentialsException;
import nl.fontys.realestateproject.business.exceptions.EmailAlreadyInUse;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountSeviceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    void updateAccount_ShouldThrowInvalidUserException_WhenUserNotFound() {
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setId(1L);
        when(userRepository.existsById(request.getId())).thenReturn(false);

        assertThrows(InvalidUserException.class, () -> accountService.updateAccount(request));
    }

    @Test
    void deleteAccount_ShouldThrowInvalidUserException_WhenUserNotFound() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(InvalidUserException.class, () -> accountService.deleteAccount(userId));
    }

    @Test
    void getAccount_ShouldThrowInvalidUserException_WhenUserNotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> accountService.getAccount(userId));
    }

    @Test
    void login_ShouldThrowCredentialsException_WhenEmailNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertThrows(CredentialsException.class, () -> accountService.login(request));
    }

    @Test
    void login_ShouldThrowCredentialsException_WhenPasswordIsIncorrect() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setPassword("correctpassword");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(accountEntity));

        assertThrows(CredentialsException.class, () -> accountService.login(request));
    }
}
