package nl.fontys.realestateproject.business;


import nl.fontys.realestateproject.business.dto.user.*;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
    GetAllAccountsResponse getAllAccounts();
    void updateAccount(UpdateAccountRequest request);
    void deleteAccount(long id);
    GetUserAccountResponse getAccount(long id);
    GetUserAccountResponse login(LoginRequest request);
}
