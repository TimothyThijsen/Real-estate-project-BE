package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.AccountService;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.domain.Property.GetAllPropertiesResponse;
import nl.fontys.realestateproject.domain.Property.Property;
import nl.fontys.realestateproject.domain.User.Account;
import nl.fontys.realestateproject.domain.User.CreateAccountRequest;
import nl.fontys.realestateproject.domain.User.CreateAccountResponse;
import nl.fontys.realestateproject.domain.User.Enums.UserRole;
import nl.fontys.realestateproject.domain.User.GetAllAccountsResponse;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
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
}
