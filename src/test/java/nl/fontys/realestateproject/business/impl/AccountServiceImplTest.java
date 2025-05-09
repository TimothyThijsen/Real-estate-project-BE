package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.dto.user.CreateAccountRequest;
import nl.fontys.realestateproject.business.dto.user.GetAllAccountsResponse;
import nl.fontys.realestateproject.business.dto.user.LoginRequest;
import nl.fontys.realestateproject.business.dto.user.UpdateAccountRequest;
import nl.fontys.realestateproject.business.exceptions.CredentialsException;
import nl.fontys.realestateproject.business.exceptions.EmailAlreadyInUse;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.business.impl.account.AccountServiceImpl;
import nl.fontys.realestateproject.configuration.security.token.AccessTokenEncoder;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.domain.enums.UserRole;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.UserRoleEntity;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    AccessTokenEncoder accessTokenEncoder;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountConverter accountConverter;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_ShouldThrowEmailAlreadyInUse_WhenEmailAlreadyExists() {
        new CreateAccountRequest();
        CreateAccountRequest request = CreateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();

        when(userRepository.save(any(AccountEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));
        assertThrows(EmailAlreadyInUse.class, () -> accountService.createAccount(request));
    }

    @Test
    void createAccount_ShouldThrowInvalidUserException_WhenCharacterLimitExceeded() {
        new CreateAccountRequest();
        CreateAccountRequest request = CreateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("nameaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        SQLException sqlException = new SQLException("Error", "SQLState", 1406);
        DataException exception = new DataException("Error message", sqlException);
        DataIntegrityViolationException e = new DataIntegrityViolationException("Error", exception);
        when(userRepository.save(any(AccountEntity.class))).thenThrow(e);
        assertThrows(InvalidUserException.class, () -> accountService.createAccount(request));
    }

    @Test
    void createAccount_shouldThrowInvalidUserException_WhenThereIsDataException() {
        new CreateAccountRequest();
        CreateAccountRequest request = CreateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        SQLException sqlException = new SQLException("Error", "SQLState", 1409);
        DataException exception = new DataException("Error message", sqlException);
        DataIntegrityViolationException e = new DataIntegrityViolationException("Error", exception);
        when(userRepository.save(any(AccountEntity.class))).thenThrow(e);
        assertThrows(InvalidUserException.class, () -> accountService.createAccount(request));
    }

    @Test
    void createAccount_ShouldThrowInvalidUserException_UnknownErrorIsThrown() {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        RuntimeException runtimeException = new RuntimeException(new SQLException("Error", "SQLState", 1409));
        when(userRepository.save(any(AccountEntity.class))).thenThrow(runtimeException);
        assertThrows(InvalidUserException.class, () -> accountService.createAccount(request));
    }

    @Test
    void createAccount_ShouldReturnId_WhenAccountIsCreated() {
        new CreateAccountRequest();
        CreateAccountRequest request = CreateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        when(userRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);
        assertEquals(1L, accountService.createAccount(request).getAccountId());
    }

    @Test
    void getAllAccounts_ShouldReturnAllAccounts() {
        when(userRepository.findAll()).thenReturn(List.of(new AccountEntity(), new AccountEntity()));

        GetAllAccountsResponse response = accountService.getAllAccounts();

        assertEquals(2, response.getAccounts().size());
    }

    @Test
    void updateAccount_ShouldThrowInvalidUserException_WhenUserNotFound() {
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setId(1L);
        when(userRepository.existsById(request.getId())).thenReturn(false);

        assertThrows(InvalidUserException.class, () -> accountService.updateAccount(request));
    }

    @Test
    void updateAccount_ShouldThrowInvalidUserException_WhenCharacterLimitExceeded() {
        new UpdateAccountRequest();
        UpdateAccountRequest request = UpdateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("nameaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        SQLException sqlException = new SQLException("Error", "SQLState", 1406);
        DataException exception = new DataException("Error message", sqlException);
        DataIntegrityViolationException e = new DataIntegrityViolationException("Error", exception);
        when(userRepository.existsById(request.getId())).thenReturn(true);
        when(userRepository.save(any(AccountEntity.class))).thenThrow(e);

        assertThrows(InvalidUserException.class, () -> accountService.updateAccount(request));
    }

    @Test
    void updateAccount_ShouldReturnUpdatedAccount_WhenAccountIsUpdated() {
        UpdateAccountRequest request = UpdateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        when(userRepository.existsById(request.getId())).thenReturn(true);
        when(userRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);
        assertDoesNotThrow(() -> accountService.updateAccount(request));
        verify(userRepository).save(any(AccountEntity.class));
    }

    @Test
    void updateAccount_ShouldThrowEmailAlreadyInUse_WhenEmailAlreadyExists() {
        new UpdateAccountRequest();
        UpdateAccountRequest request = UpdateAccountRequest.builder()
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .role("CLIENT")
                .password("12345").build();
        when(userRepository.existsById(request.getId())).thenReturn(true);
        when(userRepository.save(any(AccountEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));
        assertThrows(EmailAlreadyInUse.class, () -> accountService.updateAccount(request));
    }

    @Test
    void deleteAccount_ShouldThrowInvalidUserException_WhenUserNotFound() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(InvalidUserException.class, () -> accountService.deleteAccount(userId));
    }

    @Test
    void deleteAccount_ShouldDeleteAccount_WhenUserFound() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> accountService.deleteAccount(userId));
        verify(userRepository).deleteById(userId);
    }

    @Test
    void getAccount_ShouldThrowInvalidUserException_WhenUserNotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> accountService.getAccount(userId));
    }

    @Test
    void getAccount_ShouldReturnAccount_WhenUserFound() {
        long userId = 1L;
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(accountEntity));
        new Account();
        when(accountConverter.convert(accountEntity)).thenReturn(Account.builder().id(1L).build());
        assertEquals(userId, accountService.getAccount(userId).getAccount().getId());
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

    @Test
    void login_ShouldReturnLoginResponse_WhenCredentialsAreCorrect() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john.doe@fake.com");
        request.setPassword("12345");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setPassword("12345");
        accountEntity.setUserRoles(Set.of(UserRoleEntity.builder().role(UserRole.CLIENT).build()));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(accountEntity));
        new Account();
        when(accessTokenEncoder.encode(any())).thenReturn("token");
        when(passwordEncoder.matches(request.getPassword(), accountEntity.getPassword())).thenReturn(true);
        assertEquals("token", accountService.login(request));
        verify(userRepository).findByEmail(request.getEmail());

    }
}
