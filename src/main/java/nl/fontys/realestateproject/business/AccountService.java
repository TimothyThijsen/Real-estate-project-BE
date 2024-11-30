package nl.fontys.realestateproject.business;


import nl.fontys.realestateproject.business.dto.user.*;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    GetAllAccountsResponse getAllAccounts();

    void updateAccount(UpdateAccountRequest request);

    void deleteAccount(long id);

    GetUserAccountResponse getAccount(long id);

    String login(LoginRequest request);
    String generateAccessToken(AccountEntity user);
}
