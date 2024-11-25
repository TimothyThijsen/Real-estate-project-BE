package nl.fontys.realestateproject.business.impl.account;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.business.dto.user.*;
import nl.fontys.realestateproject.business.exceptions.CredentialsException;
import nl.fontys.realestateproject.business.exceptions.EmailAlreadyInUse;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import nl.fontys.realestateproject.configuration.security.token.AccessTokenEncoder;
import nl.fontys.realestateproject.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.domain.enums.UserRole;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.UserRoleEntity;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    UserRepository userRepository;
    AccountConverter accountConverter;
    PasswordEncoder passwordEncoder;
    AccessTokenEncoder accessTokenEncoder;

    @Override
    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        AccountEntity savedAccount = null;
        try {
            savedAccount = saveAccountToRepository(createAccountRequest);
        } catch (Exception e) {
            handleException(e);
        }
        return new CreateAccountResponse(savedAccount.getId());
    }

    private void handleException(Exception e) {
        Throwable cause = e.getCause();
        if (cause instanceof DataException dataException) {

            int errorCode = dataException.getErrorCode();
            if (errorCode == 1406) {
                throw new InvalidUserException("Limit exceeded");
            }
            throw new InvalidUserException(e.getMessage());
        }
        if (e instanceof DataIntegrityViolationException) {
            throw new EmailAlreadyInUse();
        }
        throw new InvalidUserException("Error occurred trying to create account");
    }

    private AccountEntity saveAccountToRepository(CreateAccountRequest createAccountRequest) {
        AccountEntity newAccount = AccountEntity.builder()
                .email(createAccountRequest.getEmail())
                .firstName(Character.toUpperCase(createAccountRequest.getFirstName().charAt(0)) + createAccountRequest.getFirstName().substring(1))
                .lastName(Character.toUpperCase(createAccountRequest.getLastName().charAt(0)) + createAccountRequest.getLastName().substring(1))
                .password(passwordEncoder.encode(createAccountRequest.getPassword()))
                .build();
        UserRoleEntity userRole = UserRoleEntity.builder()
                .role(UserRole.valueOf(createAccountRequest.getRole()))
                .user(newAccount)
                .build();
        newAccount.setUserRoles(Set.of(userRole));

        return userRepository.save(newAccount);
    }

    @Override
    public GetAllAccountsResponse getAllAccounts() {
        List<AccountEntity> results = userRepository.findAll();

        final GetAllAccountsResponse response = new GetAllAccountsResponse();
        List<Account> accounts = results
                .stream()
                .map(accountConverter::convert)
                .toList();
        response.setAccounts(accounts);
        return response;
    }

    @Override
    public void updateAccount(UpdateAccountRequest request) {
        if (!userRepository.existsById(request.getId())) {
            throw new InvalidUserException();
        }
        AccountEntity account = getUpdatedAccount(request);
        try {
            userRepository.save(account);
        } catch (Exception e) {
            handleException(e);
        }
    }

    private AccountEntity getUpdatedAccount(UpdateAccountRequest request) {
        AccountEntity accountEntity = AccountEntity.builder()
                .id(request.getId())
                .email(request.getEmail())
                .firstName(Character.toUpperCase(request.getFirstName().charAt(0)) + request.getFirstName().substring(1))
                .lastName(Character.toUpperCase(request.getLastName().charAt(0)) + request.getLastName().substring(1))
                .password(request.getPassword())
                .build();
        UserRoleEntity userRole = UserRoleEntity.builder()
                .role(UserRole.valueOf(request.getRole()))
                .user(accountEntity)
                .build();
        accountEntity.setUserRoles(Set.of(userRole));
        return accountEntity;
    }

    @Override
    public void deleteAccount(long id) {
        if (!userRepository.existsById(id)) {
            throw new InvalidUserException();
        }
        userRepository.deleteById(id);
    }

    @Override
    public GetUserAccountResponse getAccount(long id) {
        Optional<AccountEntity> result = userRepository.findById(id);
        if (result.isEmpty()) {
            throw new InvalidUserException();
        }

        return GetUserAccountResponse.builder()
                .account(accountConverter.convert(result.get()))
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<AccountEntity> result = userRepository.findByEmail(request.getEmail());
        if (result.isEmpty()) {
            throw new CredentialsException();
        }
        if (!passwordEncoder.matches(request.getPassword(), result.get().getPassword())) {
            throw new CredentialsException();
        }
        return LoginResponse.builder()
                .token(generateAccessToken(result.get()))
                .build();
    }

    private String generateAccessToken(AccountEntity user) {
        Long userId = user.getId();
        Collection<String> roles = user.getUserRoles().stream()
                .map(UserRoleEntity::getRole)
                .map(UserRole::name)
                .toList();
        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getEmail(), userId, roles));
    }
}
