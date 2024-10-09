package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.business.DTO.User.*;
import nl.fontys.realestateproject.business.exceptions.EmailAlreadyInUse;
import nl.fontys.realestateproject.business.exceptions.InvalidUserException;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.domain.Enums.UserRole;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        if(userRepository.AccountExists(createAccountRequest.getEmail())) {
            throw new EmailAlreadyInUse();
        }
        AccountEntity savedAccount = SaveAccountToRepository(createAccountRequest);
        return new CreateAccountResponse(savedAccount.getId());
    }

    private AccountEntity SaveAccountToRepository(CreateAccountRequest createAccountRequest) {
        AccountEntity newAccount = AccountEntity.builder()
                .email(createAccountRequest.getEmail())
                .firstName(createAccountRequest.getFirstName())
                .lastName(createAccountRequest.getLastName())
                .password(createAccountRequest.getPassword())
                .role(UserRole.valueOf(createAccountRequest.getRole()))
                .build();

        return userRepository.CreateAccount(newAccount);
    }

    @Override
    public GetAllAccountsResponse getAllAccounts() {
        List<AccountEntity> results = userRepository.GetAllAccounts();

        final GetAllAccountsResponse response = new GetAllAccountsResponse();
        List<Account> accounts = results
                .stream()
                .map(accountEntity -> AccountConverter.convert(accountEntity))
                .toList();
        response.setAccountsList(accounts);
        return response;
    }

    @Override
    public void updateAccount(UpdateAccountRequest request) {
        Optional<AccountEntity> result = userRepository.GetAccount(request.getId());
        if(result.isEmpty()) {
            throw new InvalidUserException("USER_NOT_FOUND");
        }
        AccountEntity account = GetUpdatedAccount(request);
        userRepository.UpdateAccount(account);
    }

    private AccountEntity GetUpdatedAccount(UpdateAccountRequest request) {
        return AccountEntity.builder()
                .id(request.getId())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }

    @Override
    public void deleteAccount(long id) {
        Optional<AccountEntity> result = userRepository.GetAccount(id);
        if(result.isEmpty()) {
            throw new InvalidUserException("USER_NOT_FOUND");
        }
        userRepository.DeleteAccount(id);
    }

    @Override
    public GetUserAccountResponse getAccount(long id) {
        Optional<AccountEntity> result = userRepository.GetAccount(id);
        if(result.isEmpty()) {
            throw new InvalidUserException("USER_NOT_FOUND");
        }
        return GetUserAccountResponse.builder()
                .account(AccountConverter.convert(result.get()))
                .build();
    }

    @Override
    public GetUserAccountResponse login(LoginRequest request) {
        Optional<AccountEntity> result = userRepository.GetAccountByEmail(request.getEmail());
        if(result.isEmpty()) {
            throw new InvalidUserException("USER_NOT_FOUND");
        }
        if(!result.get().getPassword().equals(request.getPassword())) {
            throw new InvalidUserException("INVALID_PASSWORD");
        }
        return GetUserAccountResponse.builder()
                .account(AccountConverter.convert(result.get()))
                .build();
    }
}
